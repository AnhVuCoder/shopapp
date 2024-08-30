package com.ngleanhvu.shopapp.config;

import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LanguageConfig {
    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages"); // Ten co so cua cac tep tai lieu ngon ngu
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
