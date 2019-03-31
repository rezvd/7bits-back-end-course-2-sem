package it.sevenbits.hwspring.core.service.validation;

import java.util.ArrayList;
import java.util.List;

public class StatusValidator {
    private static List<String> statuses = new ArrayList<>();

    public static boolean isValid(final String status) {
        statuses.add("inbox");
        statuses.add("done");
        if (status == null) {
            return false;
        }
        return statuses.contains(status);
    }
}
