package org.xlet.upgrader.vo.dashboard;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 下午6:50
 * Summary:
 */
public class VersionDTO extends BaseDTO {

    private String product;
    private String productId;
    private String version;
    private String download;
    private String pack;

    private String state;
    private long count;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
