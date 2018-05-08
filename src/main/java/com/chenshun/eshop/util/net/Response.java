package com.chenshun.eshop.util.net;

/**
 * User: mew <p />
 * Time: 18/5/8 09:43  <p />
 * Version: V1.0  <p />
 * Description: 请求的响应 <p />
 */
public class Response {

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    private String status;

    private String message;

    public Response() {
    }

    public Response(String status) {
        this.status = status;
        this.message = String.format("operate %s!", status);
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
