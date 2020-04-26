package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.security.CookieJwtAuthFilter;
import it.sevenbits.hwspring.web.security.JsonWebTokenService;
import it.sevenbits.hwspring.web.security.JwtAuthFilter;
import it.sevenbits.hwspring.web.security.JwtAuthenticationProvider;
import it.sevenbits.hwspring.web.security.JwtSettings;
import it.sevenbits.hwspring.web.security.JwtTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenService jwtTokenService;

    /**
     * Constructor for WebSecurityConfig
     * @param jwtTokenService is a service for work with JWT token
     */
    public WebSecurityConfig(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().disable();
        http.requestCache().disable();
        http.anonymous();

        RequestMatcher signinPageMatcher = new AntPathRequestMatcher("/signin");
        RequestMatcher signupPageMatcher = new AntPathRequestMatcher("/signup");
        RequestMatcher signinAndSignup = new OrRequestMatcher(signinPageMatcher, signupPageMatcher);
        RequestMatcher notLoginPageMatcher = new NegatedRequestMatcher(signinAndSignup);

//        JwtAuthFilter authFilter = new HeaderJwtAuthFilter(notLoginPageMatcher);
        JwtAuthFilter authFilter = new CookieJwtAuthFilter(notLoginPageMatcher);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);

        http
                .authorizeRequests().antMatchers("/signup").permitAll()
                .and()
                .authorizeRequests().antMatchers("/signin").permitAll()
                .and()
                .authorizeRequests().antMatchers("/users/**").hasAuthority("ADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtTokenService));
    }

    /**
     * Encoder for encoding passwords
     * @return certain implementation of PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a JwtTokenService
     * @param settings contains settings for JwtTokenService
     * @return a service for work with JWT token
     */
    @Bean
    public JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }
}
