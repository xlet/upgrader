package org.xlet.upgrader.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-5 下午7:15
 * Summary:
 */
@XmlRootElement(name = "response")
@XmlType(propOrder = {"success","latest","window", "product", "items"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private boolean success = false;

    private boolean latest = false;

    private Window window;

    private ProductVo product;

    private List<T> items;

    public Response() {
    }

    public Response(List<T> items) {
        this.items = items;
    }

    // @XmlAnyElement(lax = true)
    @XmlElementRefs({
            @XmlElementRef(type = VersionVo.class),
            @XmlElementRef(type = ChangeLogVo.class)
    })
    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Response success() {
        return success(true);
    }

    public Response success(boolean success) {
        this.success = success;
        return this;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProductVo getProduct() {
        return product;
    }

    public void setProduct(ProductVo product) {
        this.product = product;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
