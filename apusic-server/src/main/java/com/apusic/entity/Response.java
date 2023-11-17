package com.apusic.entity;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 曾文亮
 * @version 1.0.0
 * @email wenliang_zeng416@163.com
 * @date 2023年11月16日 10:13:45
 * @packageName com.apusic.entity
 * @className Respond
 * @describe TODO
 */
@Service
public class Response {
    private Map<String, Object> error = null;
    private String requestId;
    private Result result;

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> value) {
        this.error = value;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String value) {
        this.requestId = value;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result value) {
        this.result = value;
    }
}
