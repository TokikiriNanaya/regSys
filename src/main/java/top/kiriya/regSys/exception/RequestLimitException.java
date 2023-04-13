package top.kiriya.regSys.exception;

/**
 * @author Kiriya
 * @date 2023/1/12 18:01
 * 异常类
 */
public class RequestLimitException extends Exception {
    private static final long serialVersionUID = 1364225358754654702L;

    public RequestLimitException(){
        super("HTTP请求超出设定的限制");
    }

    public RequestLimitException(String message){
        super(message);
    }
}
