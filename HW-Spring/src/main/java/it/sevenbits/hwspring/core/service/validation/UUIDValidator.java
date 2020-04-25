package it.sevenbits.hwspring.core.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validation UUID
 */
public final class UUIDValidator {

    /**
     * Constructor, which will never be called. Utility class should not have public or default constructor
     */
    private UUIDValidator() {
    }

    /**
     * Validates UUID string
     *
     * @param s is the string, which will be checked as UUID
     * @return true, if the string if valid, otherwise return false
     */
    public static boolean isValid(final String s) {
        String regex = "([a-f[0-9[A-F]]]{8})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{12})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
