package it.sevenbits.hwspring.core.service.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for order validation
 */
public final class OrderValidator {
    private static List<String> orders = new ArrayList<>();


    /**
     * Constructor, which will never be called. Utility class should not have public or default constructor
     */
    private OrderValidator(){}

    /**
     * Validates order
     * @param order is the order, which will be checked
     * @return true, if the order is valid, otherwise return false
     */
    public static boolean isValid(final String order) {
        orders.add("desc");
        orders.add("asc");
        return orders.contains(order);
    }
}
