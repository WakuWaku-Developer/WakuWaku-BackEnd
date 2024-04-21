package dev.backend.wakuwaku.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiDocumentConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/dist/**").addResourceLocations("classpath:/static/dist/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/static/dist/swagger-ui/");
    }
}
