package org.xlet.upgrader.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-2 下午5:20
 * Summary:
 */
@XmlRootElement(name = "update")
@XmlAccessorType(XmlAccessType.FIELD)
public class Update implements Serializable {


    private Version version;

    private Url url;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @XmlRootElement(name="version")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Version{

        @XmlAttribute(name = "value")
        private String value;

        public Version() {
        }

        public Version(String version) {
            this.value = version;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @XmlRootElement(name = "url")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Url{
        @XmlAttribute(name = "value")
        private String value;

        public Url() {
        }

        public Url(String url) {
            this.value = url;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
