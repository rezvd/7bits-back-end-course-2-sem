package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.model.Pagination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class, which set configuration for pagination
 */
@Configuration
public class PaginationConfig {
    private static final int MIN_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_ORDER = "desc";

    /**
     * Set configuration for pagination: minimum, maximum and default page size, default order of sorting
     * @return ready pagination
     */
    @Bean
    public Pagination pagination() {
        return new Pagination(MIN_PAGE_SIZE, MAX_PAGE_SIZE, DEFAULT_PAGE_SIZE, DEFAULT_PAGE, DEFAULT_ORDER);
    }
}
