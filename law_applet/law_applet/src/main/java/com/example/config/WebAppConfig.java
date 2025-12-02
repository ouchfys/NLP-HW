package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Value("${upload.file-path}")
    private String uploadDirectory;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/**是对应resource下工程目录
        registry.addResourceHandler("file/**").addResourceLocations("file:" + uploadDirectory);
    }
}
