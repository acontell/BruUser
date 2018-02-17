package com.bruuser.business.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HalfDuplexXmlAdapter extends XmlAdapter<String, String> {

    static final String EMPTY_STRING = "";

    @Override
    public String unmarshal(String value) throws Exception {
        return value;
    }

    @Override
    public String marshal(String value) throws Exception {
        return EMPTY_STRING;
    }
}
