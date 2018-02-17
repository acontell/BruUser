package com.bruuser.business.validation;

import static java.util.Optional.ofNullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidation {

    private static final Pattern NOT_EMPTY_CHARS_AND_SPACES_ONLY = Pattern.compile("^[ A-Za-z]+$");
    private static final Pattern NOT_EMPTY_ALPHANUMERIC_ONLY = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern NOT_EMPTY_UPPER_CASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern NOT_EMPTY_DIGIT = Pattern.compile(".*[0-9].*");

    private static boolean checkNullAndPattern(String str, Pattern pattern) {
        return ofNullable(str)
                .map(pattern::matcher)
                .map(Matcher::matches)
                .orElse(false);
    }

    public static boolean isNotEmptyCharsAndSpacesOnly(String str) {
        return checkNullAndPattern(str, NOT_EMPTY_CHARS_AND_SPACES_ONLY);
    }

    public static boolean isNotEmptyAlphaNumericOnly(String str) {
        return checkNullAndPattern(str, NOT_EMPTY_ALPHANUMERIC_ONLY);
    }

    public static boolean isNotEmptyAtLeastOneDigitAndUpperCase(String str) {
        return checkNullAndPattern(str, NOT_EMPTY_UPPER_CASE) && checkNullAndPattern(str, NOT_EMPTY_DIGIT);
    }
}
