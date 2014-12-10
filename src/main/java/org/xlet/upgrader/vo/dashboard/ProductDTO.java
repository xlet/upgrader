package org.xlet.upgrader.vo.dashboard;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:35
 * Summary:
 */
public class ProductDTO extends BaseDTO {

    private String code;

    private String name;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
