package top.kiriya.regSys.exception;

/**
 * @author:吴照保
 * @Description:
 * @date:2023/4/13 19:27
 **/
public enum AppExceptionCodeMsg {

    SEVER_ERROR(500,"服务器内部错误"),
    VERIFYCODE_NOTEXIST(1001,"注册码不存在"),
    VERIFYCODE_DISABLED(1002,"注册码被禁用"),
    VERIFYCODE_EXPIRED(1003,"注册码已过期"),
    VERIFYCODE_NOTMATCH(1004,"电脑信息与注册码不匹配");


    private int code ;
    private String msg ;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    AppExceptionCodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
