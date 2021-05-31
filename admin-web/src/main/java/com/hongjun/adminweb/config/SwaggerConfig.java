package com.hongjun.adminweb.config;

import com.hongjun.common.config.BaseSwaggerConfig;
import com.hongjun.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hongjun500
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hongjun.adminweb.controller")
                .title("点评搜索头条系统")
                .description("点评搜索头条系统相关接口文档")
                .contactName("dianping")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
