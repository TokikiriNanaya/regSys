package top.kiriya.regSys.util;

/**
 * 数据封装
 *
 * @author Kirito
 * @date 2020/12/23 15:08 <br>
 */
public class JsonResult {
    private Boolean success = false;
    private String message = "";
    private Object data = null;

    public Boolean getSuccess() {
        return success;
    }

    public JsonResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
