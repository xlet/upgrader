package org.xlet.upgrader.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-15 下午3:44
 * Summary:
 */
@XmlRootElement(name="window")
@XmlAccessorType(XmlAccessType.FIELD)
public class Window {

    @XmlAttribute(name = "width")
    private int width;
    @XmlAttribute(name = "height")
    private int height;

    public Window() {
    }

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
