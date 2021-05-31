package com.hongjun.app.config;

import com.hongjun.common.config.BaseSwaggerConfig;
import com.hongjun.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hongjun500
 * @date 2021/4/14 16:09
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: app的接口文档配置
 */
@Configuration
@EnableSwagger2
public class AppSwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hongjun.app.controller")
                .title("点评搜索app系统")
                .description("点评搜索app系统相关接口文档")
                .contactName("hongjun500")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
