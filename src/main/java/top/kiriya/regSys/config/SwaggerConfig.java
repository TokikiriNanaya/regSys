package top.kiriya.regSys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Kiriya
 * @date 2023年01月04日 18:05
 * swagger配置
 */
@Configuration
@EnableSwagger2 //启用Swagger2功能
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top"))//top包下所有API都交给Swagger2管理
                .paths(PathSelectors.any()).build();
    }

    //API文档页面显示信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("注册系统")//标题
                .description("用于注册验证和管理")//描述
                .version("beta_0.1")
                .build();
    }
}