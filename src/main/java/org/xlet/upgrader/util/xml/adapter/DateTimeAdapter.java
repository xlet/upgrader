package org.xlet.upgrader.util.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 上午11:19
 * Summary:
 */
public class DateTimeAdapter extends XmlAdapter<String, Date> {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Date v) throws Exception {
        return SIMPLE_DATE_FORMAT.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        return SIMPLE_DATE_FORMAT.parse(v);
    }
}
