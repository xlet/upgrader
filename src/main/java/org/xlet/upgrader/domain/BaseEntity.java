package org.xlet.upgrader.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-4 上午11:34
 * Summary:
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Date createAt;
    protected Date updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @PrePersist
    void createAt(){
        this.createAt = this.updateAt =  new Date();
    }

    @PreUpdate
    void updateAt(){
        this.updateAt = new Date();
    }


}
