package org.xlet.upgrader.vo.dashboard;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-11 上午11:36
 * Summary:
 */
public class BaseDTO {

    protected long id;
    protected String createAt;
    protected String updateAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
