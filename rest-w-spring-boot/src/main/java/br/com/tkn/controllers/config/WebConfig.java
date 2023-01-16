package br.com.tkn.controllers.config;

import br.com.tkn.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        /// Via Query Param. http://localhost:8080/api/person/v1?mediaType=xml
//        configurer.favorParameter(true)
//                .parameterName("mediaType").ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);

        //Via Header param
            configurer.favorParameter(false)
                    .ignoreAcceptHeader(false)
                    .useRegisteredExtensionsOnly(false)
                    .defaultContentType(MediaType.APPLICATION_JSON)
                        .mediaType("json", MediaType.APPLICATION_JSON)
                        .mediaType("xml", MediaType.APPLICATION_XML)
                        .mediaType("yaml", MEDIA_TYPE_APPLICATION_YML);


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);

        registry
                .addMapping("/api/**")
                .allowedMethods("*")
                .allowedOrigins("https://erudio.com.br", "http://localhost:8080", "http://localhost:3000")
                .allowCredentials(true);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }
}
