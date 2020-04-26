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
     * Set configuration for pagination according to the values in the Application.yml
     * @param minPageSize is a minimum page size
     * @param maxPageSize is a maximum page size
     * @param defaultPageSize is a default page size
     * @param defaultPage is a default page
     * @param defaultOrder is a default order of sorting tasks
     * @return configured pagination
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