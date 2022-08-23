package hyjung.shop_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Shop Management")
                .description("Shop Management API").build();
    }

    @Bean
    public Docket swagger(){
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())

                .apiInfo(swaggerInfo()).select()
                //ApiSelectorBuilder를 생성

                .apis(RequestHandlerSelectors.any())
                //api 스펙이 작성되어 있는 패키지를 지정 함.
                //즉, 컨트롤러가 존재하는 패키지를 basepackage로 지정하여, RequestMapping( GetMapping, PostMapping ... )이 선언된 API를 문서화 함

                .paths(PathSelectors.any())
                //apis()로 선택되어진 API중 특정 path 조건에 맞는 API들을 다시 필터링하여 문서화

                .build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // -- Static resources
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
    }
}
