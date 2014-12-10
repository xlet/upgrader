package org.xlet.upgrader.util.dozer;

import org.dozer.DozerConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creator: JimmyLin
 * DateTime: 14-4-18 下午12:02
 * Summary: timemillis <=> formatted string
 */
public class SimpleDateConverter extends DozerConverter<Date, String> {

    private DateFormat dateFormater;

    public SimpleDateConverter(String pattern) {
        super(Date.class, String.class);
        dateFormater = new SimpleDateFormat(pattern);
    }

    @Override
    public String convertTo(Date source, String destination) {
        if (source == null) return destination;
        return dateFormater.format(source);
    }

    @Override
    public Date convertFrom(String source, Date destination) {
        try {
            return dateFormater.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return destination;
    }
}
