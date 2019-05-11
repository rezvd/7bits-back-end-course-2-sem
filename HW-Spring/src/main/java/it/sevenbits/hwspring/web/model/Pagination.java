package it.sevenbits.hwspring.web.model;

/**
 * Class, which help to get items with pagination
 */
public class Pagination {
    private int minPageSize;
    private int maxPageSize;
    private int defaultPageSize;
    private int defaultPage;
    private String defaultOrder;

    /**
     * Constructor with parameters for default pagination
     * @param minPageSize is the minimum page size
     * @param maxPageSize is the maximum page size
     * @param defaultPageSize is the default page size
     * @param defaultPage is the default page number
     * @param defaultOrder is the default order to sort items
     */
    public Pagination(final int minPageSize, final int maxPageSize, final int defaultPageSize,
                      final int defaultPage, final String defaultOrder) {
        this.minPageSize = minPageSize;
        this.maxPageSize = maxPageSize;
        this.defaultPageSize = defaultPageSize;
        this.defaultPage = defaultPage;
        this.defaultOrder = defaultOrder;
    }

    public int getMinPageSize() {
        return minPageSize;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public String getDefaultOrder() {
        return defaultOrder;
    }
}
