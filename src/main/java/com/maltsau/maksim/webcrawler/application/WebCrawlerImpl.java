package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.termsanalyzer.TermsAnalyzer;
import com.maltsau.maksim.webcrawler.webpageresolver.WebPageResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("webCrawler")
public class WebCrawlerImpl implements WebCrawler {
    private Integer linkDepth;
    private Integer pagesLimit;
    private String seedUrl;
    private String terms;

    private WebPageResolver webPageResolver;
    private TermsAnalyzer termsAnalyzer;

    public void setLinkDepth(Integer linkDepth) {
        this.linkDepth = linkDepth;
    }

    public void setPagesLimit(Integer pagesLimit) {
        this.pagesLimit = pagesLimit;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @Autowired
    public void setWebPageResolver(WebPageResolver webPageResolver) {
        this.webPageResolver = webPageResolver;
    }

    @Autowired
    @Qualifier("HTMLBodyTermsAnalyzer")
    public void setTermsAnalyzer(TermsAnalyzer termsAnalyzer) {
        this.termsAnalyzer = termsAnalyzer;
    }

    @Override
    public Map<String, Long> crawlWebSite() {
        String pageContentHtml = webPageResolver.getPageContent(seedUrl);
        List<String> termsList = Arrays.asList(StringUtils.split(terms, ";"));
        return termsAnalyzer.analyzeMatchesCount(pageContentHtml, termsList);
    }

    @Override
    public String toString() {
        return "WebCrawlerImpl{" +
                "linkDepth=" + linkDepth +
                ", pagesLimit=" + pagesLimit +
                ", seedUrl='" + seedUrl + '\'' +
                ", terms='" + terms + '\'' +
                ", webPageResolver=" + webPageResolver +
                '}';
    }
}