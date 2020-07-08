package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.webpageresolver.WebPageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("webCrawler")
public class WebCrawlerImpl implements WebCrawler {
    private Integer linkDepth;
    private Integer pagesLimit;
    private String seedUrl;

    private WebPageResolver webPageResolver;

    public void setLinkDepth(Integer linkDepth) {
        this.linkDepth = linkDepth;
    }

    public void setPagesLimit(Integer pagesLimit) {
        this.pagesLimit = pagesLimit;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    @Autowired
    public void setWebPageResolver(WebPageResolver webPageResolver) {
        this.webPageResolver = webPageResolver;
    }

    @Override
    public String crawlWebSite() {
        return webPageResolver.getPageContent(seedUrl);
    }

    @Override
    public String toString() {
        return "WebCrawler{" +
                "linkDepth=" + linkDepth +
                ", pagesLimit=" + pagesLimit +
                '}';
    }
}