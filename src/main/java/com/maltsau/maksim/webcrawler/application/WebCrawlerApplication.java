package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.WebCrawlerImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("webcrawler-spring-context.xml");

        System.out.println(applicationContext.getBean(WebCrawlerImpl.class));

        applicationContext.close();
    }
}
