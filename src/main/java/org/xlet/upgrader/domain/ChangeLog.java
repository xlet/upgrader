package org.xlet.upgrader.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-3 下午8:05
 * Summary:
 */
@Entity
@Table(name="ws_change_log")
public class ChangeLog extends BaseEntity {

    private String title;
    private String description;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id")
    private org.xlet.upgrader.domain.Version version;
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

    @JsonBackReference("version")
    public org.xlet.upgrader.domain.Version getVersion() {
        return version;
    }

    public void setVersion(org.xlet.upgrader.domain.Version version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
