package it.sevenbits.hwspring.core.service.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for status validation
 */
public final class StatusValidator {
    private static List<String> statuses = new ArrayList<>();


    /**
     * Constructor, which will never be called. Utility class should not have public or default constructor
     */
    private StatusValidator(){}

    /**
     * Validates status
     * @param status is the status, with will be checked
     * @return true, if the status is valid, otherwise return false
     */
    public static boolean isValid(final String status) {
        statuses.add("inbox");
        statuses.add("done");
        if (status == null) {
            return false;
        }
        return statuses.contains(status);
    }
}
