package com.example.nebula.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Descriptin: swagger配置
 * @ClassName: SwaggerConfig
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        // 添加全局参数token令牌
        List<RequestParameter> requestParameter = new ArrayList<>();
        requestParameter.add(new RequestParameterBuilder()
            .name("token")
            .description("令牌")
            .required(false)
            .in(ParameterType.HEADER)
            .build());
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build().globalRequestParameters(requestParameter);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .build();
    }


}
