package com.bruuser.business.converters;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CryptoConverterTest {

    private static final String STRING_TO_CONVERT = "arcadio";
    private CryptoConverter cut;

    @Before
    public void setUp() {
        cut = new CryptoConverter();
    }

    @Test
    public void encryptAndDecryptShouldBeEqual() {
        assertEquals(STRING_TO_CONVERT, cut.convertToEntityAttribute(cut.convertToDatabaseColumn(STRING_TO_CONVERT)));
    }
}
