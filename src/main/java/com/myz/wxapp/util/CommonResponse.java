package com.myz.wxapp.util;

public class CommonResponse {
    private static final String SUCCESS = "ok";

    private static final String ERROR = "error";

    private String status;

    private Object data;

    public CommonResponse() {
        this.status = SUCCESS;
    }

    public CommonResponse(Object data) {
        this(data, SUCCESS);
    }

    public CommonResponse(Object data, String status) {
        this.data = data;
        this.status = status;
    }

    public CommonResponse success() {
        return success("");
    }

    public CommonResponse success(Object data) {
        this.status = SUCCESS;
        this.data = data;
        return this;
    }

    public CommonResponse failure(Object data) {
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
