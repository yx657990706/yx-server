package com.yx.common.base.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.yx.common.base.constant.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jesse
 * @date 2020/04/03
 *
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p> swagger-ui 访问地址：http://localhost:8080/swagger-ui.html
 * <p> knife4j 访问地址：http://${host}:${port}/doc.html
 * --------------------------------------------------
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {

    @Value("${dynamic.base.isPro:false}")
    private boolean IS_PRO;

    @Bean
    public Docket createRestApi() {
        //请求header中添加token
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name(TokenConstant.TOKEN_HEADER_KEY)
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        pars.add(tokenPar.build());
        log.debug("===>>环境标识IS_PRO:{}", IS_PRO);

        if (IS_PRO) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    //生产环境需要关闭swagger
                    .apis(RequestHandlerSelectors.basePackage(""))
                    .paths(PathSelectors.none())
                    .build()
                    .globalOperationParameters(pars);
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.yx"))//这里指定Controller扫描包路径
                    .paths(PathSelectors.any())
                    .build()
                    .globalOperationParameters(pars);
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-bootstrap-ui RESTful APIs")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl("http://localhost:9088")//服务器域名
                .contact(new Contact("jesse", "", "123456@qq.com"))
                .version("1.0")
                .build();
    }


}
