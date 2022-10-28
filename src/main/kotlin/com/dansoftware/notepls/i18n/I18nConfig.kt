package com.dansoftware.notepls.i18n

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource

@Configuration
class I18nConfig {

    @get:Bean("messageSource")
    val messageSource get() = ResourceBundleMessageSource().apply {
        setBasenames("i18n/translations")
        setDefaultEncoding("UTF-8")
    }

}