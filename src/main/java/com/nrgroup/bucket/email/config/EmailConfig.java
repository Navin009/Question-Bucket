package com.nrgroup.bucket.email.config;

import com.nrgroup.bucket.email.EmailCredentailProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;

@Configuration
public class EmailConfig {

    // @Value("${freemarker.templates.path}")
    // private String mailTemplatesPath;

    @Autowired
    public EmailCredentailProvider emailCredentailProvider;

    // @Bean
    // public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
    //     freemarker.template.Configuration configuration = new freemarker.template.Configuration(
    //             freemarker.template.Configuration.VERSION_2_3_31);
    //     TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), mailTemplatesPath);
    //     configuration.setTemplateLoader(templateLoader);
    //     FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    //     freeMarkerConfigurer.setConfiguration(configuration);
    //     return freeMarkerConfigurer;
    // }

    // @Bean
    // public ResourceBundleMessageSource emailMessageSource() {
    //     ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    //     messageSource.setBasename("mailMessages");
    //     return messageSource;
    // }

    @Bean
    SesV2Client sesV2Client() {
        return SesV2Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(emailCredentailProvider)
                .build();
    }

}
