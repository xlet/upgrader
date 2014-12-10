package org.xlet.upgrader.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 下午3:01
 * Summary:
 */
@XmlRootElement(name = "update")
@XmlAccessorType(XmlAccessType.FIELD)
public class LatestVersion {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "version")
    private String version;
    @XmlAttribute(name = "url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
