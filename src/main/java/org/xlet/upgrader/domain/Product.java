package org.xlet.upgrader.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-4 上午11:40
 * Summary:
 */
@Entity
@Table(name = "ws_product")
@XmlRootElement(name = "product")
public class Product extends BaseEntity {
    //code of product
    private String code;
    //chinese name
    private String name;
    //product page
    private String homepage;

    private int height;

    private int width;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    @OrderBy("version desc")
    private List<org.xlet.upgrader.domain.Version> versions = Lists.newArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @JsonManagedReference
    public List<org.xlet.upgrader.domain.Version> getVersions() {
        return versions;
    }

    public void setVersions(List<org.xlet.upgrader.domain.Version> versions) {
        this.versions = versions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
