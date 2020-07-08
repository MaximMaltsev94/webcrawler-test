package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.termsanalyzer.HTMLBodyTermsAnalyzer;
import com.maltsau.maksim.webcrawler.termsanalyzer.TermsAnalyzer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class WebCrawlerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("webcrawler-spring-context.xml");

        WebCrawler webCrawler = applicationContext.getBean(WebCrawler.class);
        TermsAnalyzer termsAnalyzer = applicationContext.getBean(HTMLBodyTermsAnalyzer.class);

        Map<String, Long> termsMatchCount = webCrawler.crawlWebSite();
        System.out.println(termsMatchCount);

        applicationContext.close();
    }
}
