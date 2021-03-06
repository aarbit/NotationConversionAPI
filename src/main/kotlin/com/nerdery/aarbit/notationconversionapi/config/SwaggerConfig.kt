package com.nerdery.aarbit.notationconversionapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

@Configuration
@EnableSwagger2WebMvc
class SwaggerConfig: WebMvcConfigurationSupport() {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.nerdery.aarbit.notationconversionapi.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiMetaData())
            .pathMapping("/")
    }

    private fun apiMetaData(): ApiInfo {
        return ApiInfoBuilder()
            .title("Notation Conversion API")
            .description("Mathematical notation converting REST API")
            .version("1.0")
            .contact(Contact("Alex Arbit", "", "aarbit@nerdery.com"))
            .build()
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}