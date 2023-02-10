package api;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseObject {
    @JSONField(name = "status")
    private boolean status;
    @JSONField(name = "message")
    private String message;

    public ResponseObject(boolean status, String message){
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
