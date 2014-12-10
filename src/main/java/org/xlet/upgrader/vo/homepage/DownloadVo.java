package org.xlet.upgrader.vo.homepage;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-23 上午10:34
 * Summary:
 */
public class DownloadVo {

    private String url;
    private String version;
    private String product;
    private String code;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DownloadVo{" +
                "url='" + url + '\'' +
                ", version='" + version + '\'' +
                ", product='" + product + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
