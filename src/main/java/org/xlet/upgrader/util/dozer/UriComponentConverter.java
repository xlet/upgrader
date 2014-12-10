package org.xlet.upgrader.util.dozer;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-22 下午5:27
 * Summary:
 */
public class UriComponentConverter extends DozerConverter<String, String> {

    private String context;

    private String relative;

    public UriComponentConverter() {
        super(String.class, String.class);
    }


    public UriComponentConverter(String context, String relative) {
        super(String.class, String.class);
        this.context = context;
        this.relative = relative;
    }

    @Override
    public String convertTo(String source, String destination) {
        return StringUtils.isNotEmpty(source) ? context + relative + source : source;
    }

    @Override
    public String convertFrom(String source, String destination) {
        return StringUtils.isNotEmpty(source) ? context + relative + source : source;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }
}
