package com.myz.wxapp.util;

public class Response {
    private static final String SUCCESS = "ok";

    private static final String ERROR = "error";

    private String status;

    private Object data;

    public Response() {
        this.status = SUCCESS;
    }

    public Response(Object data) {
        this(data, SUCCESS);
    }

    public Response(Object data, String status) {
        this.data = data;
        this.status = status;
    }

    public Response success() {
        return success("");
    }

    public Response success(Object data) {
        this.status = SUCCESS;
        this.data = data;
        return this;
    }

    public Response failure(Object data) {
        this.status = ERROR;
        this.data = data;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
