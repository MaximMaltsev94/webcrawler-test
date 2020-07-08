package com.maltsau.maksim.webcrawler.application;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebCrawlerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("webcrawler-spring-context.xml");

        WebCrawler webCrawler = applicationContext.getBean(WebCrawler.class);

        System.out.println(webCrawler.crawlWebSite());

        applicationContext.close();
    }
}
