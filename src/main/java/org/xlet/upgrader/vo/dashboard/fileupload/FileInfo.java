package org.xlet.upgrader.vo.dashboard.fileupload;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-19 上午11:13
 * Summary:
 */
public class FileInfo {

    private String url;
    private String relative;
    private String name;
    private String type;
    private long size;
    private String delete_url;
    private String delete_type;
    private int width;
    private int height;

    public FileInfo() {
    }

    public FileInfo(String domain, String name, String type, long size, String delete_type) {
        this.url = domain + name;
        this.relative = name;
        this.name = name;
        this.type = type;
        this.size = size;
        this.delete_url = url;
        this.delete_type = delete_type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }

    public String getDelete_type() {
        return delete_type;
    }

    public void setDelete_type(String delete_type) {
        this.delete_type = delete_type;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }
}
