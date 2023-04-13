package top.kiriya.regSys.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.kiriya.regSys.exception.AppException;
import top.kiriya.regSys.util.JsonResult;

/**
 * @author:吴照保
 * @Description:全局异常
 * @date:2023/4/13 19:26
 **/
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> JsonResult exceptionHandler(Exception e){
        //这里先判断拦截到的Exception是不是我们自定义的异常类型
        if(e instanceof AppException){
            AppException appException = (AppException)e;
            return new JsonResult().setSuccess(false).setMessage(appException.getMsg());
        }

        //如果拦截的异常不是我们自定义的异常(例如：数据库主键冲突)
        return new JsonResult().setSuccess(false).setMessage("服务器端异常");
    }
}