package com.maltsau.maksim.webcrawler;

import org.springframework.stereotype.Component;

@Component("webCrawler")
public class WebCrawlerImpl implements WebCrawler {
    private Integer linkDepth;
    private Integer pagesLimit;

    public void setLinkDepth(Integer linkDepth) {
        this.linkDepth = linkDepth;
    }

    public void setPagesLimit(Integer pagesLimit) {
        this.pagesLimit = pagesLimit;
    }

    @Override
    public String crawlWebSite() {
        return null;
    }

    @Override
    public String toString() {
        return "WebCrawler{" +
                "linkDepth=" + linkDepth +
                ", pagesLimit=" + pagesLimit +
                '}';
    }
}
