package org.xlet.upgrader.vo.dashboard;

/**
 * Creator: JimmyLin
 * DateTime: 14-9-15 下午5:14
 * Summary:
 */
public class Response {

    private boolean success;

    private int code;

    private String msg;



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Response success(){
        this.success = true;
        return this;
    }
}
