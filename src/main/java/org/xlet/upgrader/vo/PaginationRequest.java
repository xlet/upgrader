package org.xlet.upgrader.vo;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:28
 * Summary:
 */
public class PaginationRequest{

    private int size = 10;
    private int page = 0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
