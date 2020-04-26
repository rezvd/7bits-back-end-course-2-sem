package it.sevenbits.hwspring.config;

import it.sevenbits.hwspring.web.model.tasks.Pagination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class, which set configuration for pagination
 */
@Configuration
public class PaginationConfig {

    /**
     * Set configuration for pagination: minimum, maximum and default page size, default order of sorting
     *
     * @return ready pagination
     */
    @Bean
    public Pagination pagination(@Value("${pagination.min-page-size}") final int minPageSize,
                                 @Value("${pagination.max-page-size}") final int maxPageSize,
                                 @Value("${pagination.default-page-size}") final int defaultPageSize,
                                 @Value("${pagination.default-page}") final int defaultPage,
                                 @Value("${pagination.default-order}") final String defaultOrder) {
        return new Pagination(minPageSize, maxPageSize, defaultPageSize, defaultPage, defaultOrder);
    }
}
