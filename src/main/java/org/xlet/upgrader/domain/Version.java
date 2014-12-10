package org.xlet.upgrader.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-4 上午11:33
 * Summary:
 */
@Entity
@Table(name = "ws_version")
public class Version extends BaseEntity {

    private String version;
    //belong to which product
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    //install program
    private String download;
    //package for update
    private String pack;

    private long count;

    private VersionState state = VersionState.INIT;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "version")
    private List<ChangeLog> changeLogs = Lists.newArrayList();

    public Version() {
    }

    public Version(String version, Product product, String download) {
        this.version = version;
        this.product = product;
        this.download = download;
    }

    public Version(String version, String download, Date createAt, Date updateAt) {
        super.createAt = createAt;
        super.updateAt = updateAt;
        this.version = version;
        this.download = download;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @JsonBackReference("product")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    @JsonManagedReference("changeLogs")
    public List<ChangeLog> getChangeLogs() {
        return changeLogs;
    }

    public void setChangeLogs(List<ChangeLog> changeLogs) {
        this.changeLogs = changeLogs;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    @Enumerated(value = EnumType.STRING)
    public VersionState getState() {
        return state;
    }

    public void setState(VersionState state) {
        this.state = state;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * count+1
     */
    @Transient
    public void count() {
        this.count += 1;
    }
}
