package it.sevenbits.hwspring.core.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDValidator {


    public static boolean isValid(final String status) {
        String regex = "([a-f[0-9[A-F]]]{8})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{4})-([a-f[0-9[A-F]]]{12})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(status);
        return matcher.matches();
    }
}
