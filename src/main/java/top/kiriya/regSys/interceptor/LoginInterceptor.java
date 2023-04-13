package top.kiriya.regSys.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kiriya
 * @date 2023年01月04日 17:27
 * test 登录拦截 需要先在WebConfig中配置才能生效
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("login拦截器生效");
        return true;
    }
}
