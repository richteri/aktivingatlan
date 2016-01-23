package com.aktivingatlan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfiguration {
    
    @Bean
    Cloudinary cloudinary(CloudinaryProperties cloudinaryProperties) {
        return new Cloudinary(cloudinaryProperties.getUrl());
    }
}
