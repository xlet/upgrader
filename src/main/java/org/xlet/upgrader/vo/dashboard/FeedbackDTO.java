package org.xlet.upgrader.vo.dashboard;

/**
 * Creator: JimmyLin
 * DateTime: 14-11-3 上午11:05
 * Summary:
 */
public class FeedbackDTO extends BaseDTO {
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
