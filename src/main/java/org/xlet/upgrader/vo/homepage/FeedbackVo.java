package org.xlet.upgrader.vo.homepage;

import java.io.Serializable;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-9 下午4:40
 * Summary:
 */
public class FeedbackVo implements Serializable {

    private String product;
    private String version;
    private String ip;
    private String feedback;

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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
