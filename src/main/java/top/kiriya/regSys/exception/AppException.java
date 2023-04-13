package top.kiriya.regSys.exception;

/**
 * @author:吴照保
 * @Description:自定义异常
 * @date:2023/4/13 19:27
 **/
public class AppException extends RuntimeException{

    private int code = 500;
    private String msg = "服务器异常";


    public AppException(AppExceptionCodeMsg appExceptionCodeMsg){
        super();
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();

    }

    public AppException(int code,String msg){
        super();
        this.code = code;
        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}



