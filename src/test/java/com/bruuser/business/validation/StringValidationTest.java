package com.bruuser.business.validation;

import static com.bruuser.business.validation.StringValidation.isNotEmptyAlphaNumericOnly;
import static com.bruuser.business.validation.StringValidation.isNotEmptyAtLeastOneDigitAndUpperCase;
import static com.bruuser.business.validation.StringValidation.isNotEmptyCharsAndSpacesOnly;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StringValidationTest {

    // Could be more exhaustive but I don't think it's needed to get the point.
    private static final String CHARS_AND_SPACES = "adf As f ";
    private static final String ONLY_NUMBERS = "12345";
    private static final String ONLY_CHARS = "ASDFASDF";
    private static final String ONLY_SPACES = "   ";
    private static final String EMPTY = "";
    private static final String CHARS_AND_NUMBERS = "1324asdf1";
    private static final String CHARS_AND_NUMBERS_AND_SPACES = "124 asf 134";
    private static final String CHAR_AND_UPPER_CASE = "1 A";
    private static final String CHARS_AND_UPPER_CASES = "1 A 3 F";

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsFalseIfEmpty() {
        assertFalse(isNotEmptyCharsAndSpacesOnly(EMPTY));
    }

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsFalseIfNumbers() {
        assertFalse(isNotEmptyCharsAndSpacesOnly(ONLY_NUMBERS));
    }

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsFalseIfCharsAndNumbers() {
        assertFalse(isNotEmptyCharsAndSpacesOnly(CHARS_AND_NUMBERS));
    }

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsTrueIfChars() {
        assertTrue(isNotEmptyCharsAndSpacesOnly(ONLY_CHARS));
    }

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsTrueIfSpaces() {
        assertTrue(isNotEmptyCharsAndSpacesOnly(ONLY_SPACES));
    }

    @Test
    public void isNotEmptyCharsAndSpacesOnlyReturnsTrueIfCharsAndSpaces() {
        assertTrue(isNotEmptyCharsAndSpacesOnly(CHARS_AND_SPACES));
    }

    @Test
    public void isNotEmptyAlphaNumericOnlyReturnsFalseIfEmpty() {
        assertFalse(isNotEmptyAlphaNumericOnly(EMPTY));
    }

    @Test
    public void isNotEmptyAlphaNumericOnlyReturnsFalseIfCharsAndNumbersAndSpaces() {
        assertFalse(isNotEmptyAlphaNumericOnly(CHARS_AND_NUMBERS_AND_SPACES));
    }

    @Test
    public void isNotEmptyAlphaNumericOnlyReturnsTrueIfOnlyAlphaNumeric() {
        assertTrue(isNotEmptyAlphaNumericOnly(CHARS_AND_NUMBERS));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsFalseIfEmpty() {
        assertFalse(isNotEmptyAtLeastOneDigitAndUpperCase(EMPTY));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsFalseIfOnlyNumbers() {
        assertFalse(isNotEmptyAtLeastOneDigitAndUpperCase(ONLY_NUMBERS));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsFalseIfOnlyNumbersAndLowerCase() {
        assertFalse(isNotEmptyAtLeastOneDigitAndUpperCase(CHARS_AND_NUMBERS_AND_SPACES));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsFalseIfOnlyUpperCase() {
        assertFalse(isNotEmptyAtLeastOneDigitAndUpperCase(ONLY_CHARS));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsTrueIfOneNumberOneUppercase() {
        assertTrue(isNotEmptyAtLeastOneDigitAndUpperCase(CHAR_AND_UPPER_CASE));
    }

    @Test
    public void isNotEmptyAtLeastOneDigitAndUpperCaseReturnsTrueIfNumbersUppercases() {
        assertTrue(isNotEmptyAtLeastOneDigitAndUpperCase(CHARS_AND_UPPER_CASES));
    }
}
