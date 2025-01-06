package com.megamaker.studyforu.common.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class eventConfig {
    @Bean
    public InitializingBean eventInitializer(ApplicationContext applicationContext) {
        return () -> Events.setPublisher(applicationContext);
    }
}
