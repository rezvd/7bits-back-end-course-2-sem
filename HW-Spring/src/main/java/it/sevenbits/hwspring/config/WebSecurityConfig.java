package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenService jwtTokenService;

    public WebSecurityConfig(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtTokenService));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenService jwtTokenService(final JwtSettings settings) {
        return new JsonWebTokenService(settings);
    }
}
