package top.biandeshen.sandstorm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                访问路径
//                .host("localhost:8080")
                .select()
                //controller路径
                .apis(RequestHandlerSelectors.basePackage("top.biandeshen.sandstorm.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SandStorm后台接口文档")
                .termsOfServiceUrl("...")
                .description("此部分为SandStorm系统后台接口")
                .contact(new Contact("fanjiangpan", "http://biandeshen.top", "biandeshen@gmail.com"))
                .build();

    }
}