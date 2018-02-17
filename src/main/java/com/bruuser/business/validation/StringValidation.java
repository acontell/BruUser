package com.bruuser.business.validation;

import java.util.regex.Pattern;

public class StringValidation {

    private static final Pattern NOT_EMPTY_CHARS_AND_SPACES_ONLY = Pattern.compile("^[ A-Za-z]+$");
    private static final Pattern NOT_EMPTY_ALPHANUMERIC_ONLY = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern NOT_EMPTY_UPPER_CASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern NOT_EMPTY_DIGIT = Pattern.compile(".*[0-9].*");

    public static boolean isNotEmptyCharsAndSpacesOnly(String str) {
        return NOT_EMPTY_CHARS_AND_SPACES_ONLY.matcher(str).matches();
    }

    public static boolean isNotEmptyAlphaNumericOnly(String str) {
        return NOT_EMPTY_ALPHANUMERIC_ONLY.matcher(str).matches();
    }

    public static boolean isNotEmptyAtLeastOneDigitAndUpperCase(String str) {
        return NOT_EMPTY_UPPER_CASE.matcher(str).matches() && NOT_EMPTY_DIGIT.matcher(str).matches();
    }
}
