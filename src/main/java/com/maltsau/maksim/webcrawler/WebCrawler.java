package com.maltsau.maksim.webcrawler;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawler {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("webcrawler-spring-context.xml");

        System.out.println(applicationContext.getBean(MyBean.class));

        applicationContext.close();
    }
}
