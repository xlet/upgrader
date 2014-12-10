package org.xlet.upgrader.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-9 下午4:41
 * Summary: 客户端反馈
 */
@Entity
@Table(name = "ws_feedback")
@XmlRootElement(name = "feedback")
public class Feedback extends BaseEntity {
    private String product;
    private String version;
    private String ip;
    private String remark;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
