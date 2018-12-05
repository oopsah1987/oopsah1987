package com.haniln.hicp.spring.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration
 * @author  : Lee Jung Min
 * @version : 2018-11-22 creation
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** Cross Origin Resource Sharing Filter (allow ajax) */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*"); // ex> http://www.test.com
                //.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
                //.allowedHeaders("Content-Type", "X-Auth-Token")
                //.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
                //.allowCredentials(true).maxAge(5); // 3600
   }
}
