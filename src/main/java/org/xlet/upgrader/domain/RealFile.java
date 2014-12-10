package org.xlet.upgrader.domain;

/**
 * Creator: JimmyLin
 * DateTime: 14-11-6 下午5:14
 * Summary:
 */
public class RealFile {

    private String path;

    private long length;

    private String lastModified;

    public RealFile() {
    }

    public RealFile(String path, long length, String lastModified) {
        this.path = path;
        this.length = length;
        this.lastModified = lastModified;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
