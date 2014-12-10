package org.xlet.upgrader.vo.dashboard.form;

import org.xlet.upgrader.domain.VersionState;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-12 上午9:46
 * Summary:
 */
public class VersionForm {

    @NotEmpty(message = "version can't be empty.")
    private String version;
    @NotEmpty(message = "product id can't be empty.")
    private Long productId;
    @NotEmpty(message = "download url should not be empty")
    private String download;
    @NotEmpty(message = "package should not be empty")
    private String pack;

    private VersionState state = VersionState.INIT;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public VersionState getState() {
        return state;
    }

    public void setState(VersionState state) {
        this.state = state;
    }
}
