package org.xlet.upgrader.vo.dashboard.form;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-15 下午5:07
 * Summary: 自动提交更新表单
 */
public class AutoSubmitForm {
    //their file address
    private String url;
    private String product;
    private String version;
    //json
    private String changelog;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }
}
