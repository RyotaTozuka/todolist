package com.todolist;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ValidationMessages.propertiesでUTF-8エンコードする設定をするクラス
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}