package top.kiriya.regSys.config;

import top.kiriya.regSys.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kiriya
 * @date 2023年01月04日 17:31
 * 配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/test/**");
    }
}
