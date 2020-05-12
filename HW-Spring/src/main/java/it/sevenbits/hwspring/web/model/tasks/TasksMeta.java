package it.sevenbits.hwspring.web.model.tasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

/**
 * Model to store all information about tasks array in GetAllTasksResponse
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TasksMeta {

    private int total;
    private int page;
    private int size;
    private String next;
    private String prev;
    private String first;
    private String last;

    private static final String PATH = "/tasks";
    private static final String STATUS = "status";
    private static final String ORDER = "order";
    private static final String PAGE = "page";
    private static final String SIZE = "size";

    /**
     * Constructor for TasksMeta
     * @param total is number of all tasks
     * @param size if a size of the page
     * @param page is a number of current page
     * @param pageNumber is number of all pages
     * @param status is status of tasks
     * @param order is a sorting order
     */
    public TasksMeta(final int total, final int size, final int page, final int pageNumber,
                     final String status, final String order) {
        this.total = total;
        this.page = page;
        this.size = size;
        if (page < pageNumber) {
            this.next = getNextPage(status, order, page, size).toString();
        }
        if (page > 1) {
            this.next = getPrevPage(status, order, page, size).toString();
        }
        this.first = getFirstPage(status, order, size).toString();
        this.last = getLastPage(status, order, pageNumber, size).toString();
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public String getNext() {
        return next;
    }

    public void setNext(final String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(final String prev) {
        this.prev = prev;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(final String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(final String last) {
        this.last = last;
    }



    /**
     * Builds URI link to the next page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param page     is a current page
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the next page
     */
    public static URI getNextPage(final String status, final String order, final int page, final int pageSize) {
        return UriComponentsBuilder.fromPath(PATH)
                .queryParam(STATUS, status)
                .queryParam(ORDER, order)
                .queryParam(PAGE, page + 1)
                .queryParam(SIZE, pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the previous page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param page     is a current page
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the previous page
     */
    public static URI getPrevPage(final String status, final String order, final int page, final int pageSize) {
        return UriComponentsBuilder.fromPath(PATH)
                .queryParam(STATUS, status)
                .queryParam(ORDER, order)
                .queryParam(PAGE, page == 1 ? 1 : page - 1)
                .queryParam(SIZE, pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the first page
     *
     * @param status   is a status of the tasks
     * @param order    is an order to sort tasks
     * @param pageSize is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the first page
     */
    public static URI getFirstPage(final String status, final String order, final int pageSize) {
        return UriComponentsBuilder.fromPath(PATH)
                .queryParam(STATUS, status)
                .queryParam(ORDER, order)
                .queryParam(PAGE, 1)
                .queryParam(SIZE, pageSize)
                .build().toUri();
    }

    /**
     * Builds URI link to the last page
     *
     * @param status      is a status of the tasks
     * @param order       is an order to sort tasks
     * @param pagesNumber is a number of pages with such status and size
     * @param pageSize    is number of tasks on one page
     * @return URI from "/tasks" with these parameters as a query, which leads to the last page
     */
    public static URI getLastPage(final String status, final String order, final int pagesNumber, final int pageSize) {
        return UriComponentsBuilder.fromPath(PATH)
                .queryParam(STATUS, status)
                .queryParam(ORDER, order)
                .queryParam(PAGE, pagesNumber)
                .queryParam(SIZE, pageSize)
                .build().toUri();
    }


}
