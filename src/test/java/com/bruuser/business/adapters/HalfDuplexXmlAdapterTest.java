package com.bruuser.business.adapters;

import static com.bruuser.business.adapters.HalfDuplexXmlAdapter.EMPTY_STRING;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HalfDuplexXmlAdapterTest {

    private static final String TEST_VALUE = "asdf";
    
    private HalfDuplexXmlAdapter cut;

    @Before
    public void setUp() {
        cut = new HalfDuplexXmlAdapter();
    }
    
    @Test
    public void unmarshalShouldReturnValue() throws Exception {
        assertEquals(cut.unmarshal(TEST_VALUE), TEST_VALUE);
    }
    
    @Test
    public void marshalShouldReturnEmptyString() throws Exception {
        assertEquals(cut.marshal(TEST_VALUE), EMPTY_STRING);
    }
}
