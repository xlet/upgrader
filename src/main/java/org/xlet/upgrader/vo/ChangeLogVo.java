package org.xlet.upgrader.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-10 上午11:22
 * Summary:
 */
@XmlRootElement(name = "change_log")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChangeLogVo {

    @XmlAttribute(name = "title")
    private String title;
    @XmlAttribute(name = "image")
    private String image;
    @XmlAttribute(name = "description")
    private String description;
    @XmlAttribute(name = "height")
    private int height;
    @XmlAttribute(name = "width")
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
