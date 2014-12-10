package org.xlet.upgrader.vo;

import org.xlet.upgrader.util.xml.adapter.DateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 上午11:16
 * Summary:
 */
@XmlRootElement(name = "version")
@XmlAccessorType(XmlAccessType.FIELD)
public class VersionVo {

    @XmlAttribute(name = "version")
    private String version;
    @XmlAttribute(name = "url")
    private String url;
    @XmlElement(name = "change_log")
    private List<ChangeLogVo> changeLogs;
    @XmlAttribute(name = "create_at")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Date createAt;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ChangeLogVo> getChangeLogs() {
        return changeLogs;
    }

    public void setChangeLogs(List<ChangeLogVo> changeLogs) {
        this.changeLogs = changeLogs;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
