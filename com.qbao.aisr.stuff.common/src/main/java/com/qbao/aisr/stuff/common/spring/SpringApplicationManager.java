package com.qbao.aisr.stuff.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component(value = "springApplicationManager")
public class SpringApplicationManager implements ApplicationListener<ContextRefreshedEvent> {
    private static ApplicationContext applicationContext = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(applicationContext == null){
            applicationContext = event.getApplicationContext();
        }
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}