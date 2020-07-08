package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.domain.CrawlResponse;
import com.maltsau.maksim.webcrawler.exception.WebPageResolverException;
import com.maltsau.maksim.webcrawler.termsanalyzer.TermsAnalyzer;
import com.maltsau.maksim.webcrawler.webpageresolver.WebPageResolver;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component("webCrawler")
public class WebCrawlerImpl implements WebCrawler {
    private Logger LOG = LoggerFactory.getLogger(WebCrawlerImpl.class);

    private WebPageResolver webPageResolver;

    private TermsAnalyzer termsAnalyzer;

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
    public CrawlResponse crawlWebSite(String url, List<String> termsList) {
        try {
            String pageContentHtml = webPageResolver.getPageContent(url);
            Map<String, Long> termsMatchCount = termsAnalyzer.analyzeMatchesCount(pageContentHtml, termsList);
            return new CrawlResponse(url, pageContentHtml, termsMatchCount);
        } catch (WebPageResolverException wpre) {
            LOG.error("error while getting page: `{}`", url, wpre);
            return new CrawlResponse(url, null, Collections.emptyMap());
        }
    }

    @Override
    public List<CrawlResponse> crawlWebSiteRecursively(String url, List<String> termsList, Integer linkDepth,
                                                       Integer pagesLimit) {
        List<CrawlResponse> resultList = new ArrayList<>();
        crawlWebSiteRecursive(url, termsList, 0, 0, linkDepth, pagesLimit, resultList);
        return resultList;
    }

    private void crawlWebSiteRecursive(String url, List<String> termsList,
                                      Integer currentDepth, Integer currentPageAnalyzedCount,
                                      Integer maxLinkDepth, Integer maxPagesToScan,
                                      List<CrawlResponse> resultList) {

        if(currentDepth >= maxLinkDepth || currentPageAnalyzedCount >= maxPagesToScan) {
            return;
        }
        LOG.info("parsing url: `{}`, currentDepth : `{}`, currentPagesAnalyzed : `{}`", url, currentDepth, currentPageAnalyzedCount);
        CrawlResponse crawlResponse = crawlWebSite(url, termsList);
        resultList.add(crawlResponse);

        //TODO make parallel calls using Fork-Join Framework / manual handle using ExecutorService ThreadPools
        //TODO focus on breadth rather than depth.
        List<String> nestedUrls = getAllLinks(url, crawlResponse.getPageContentHtml());
        for (String nestedUrl : nestedUrls) {
            currentPageAnalyzedCount++;
            crawlWebSiteRecursive(nestedUrl, termsList,
                    currentDepth + 1, currentPageAnalyzedCount,
                    maxLinkDepth, maxPagesToScan,
                    resultList);
        }
    }

    private List<String> getAllLinks(String hostUrl, String pageContentHtml) {
        if(StringUtils.isAnyBlank(hostUrl, pageContentHtml)) {
            return Collections.emptyList();
        }
        List<String> resultLinks = new ArrayList<>();
        Elements linkTags = Jsoup.parse(pageContentHtml).body().getElementsByTag("a");
        for (Element linkTag : linkTags) {
            String href = linkTag.attr("abs:href");
            if(StringUtils.isBlank(href) || StringUtils.startsWith(href, "#")) {
                continue;
            }
            if(StringUtils.startsWith(href, "/")) {
                resultLinks.add(hostUrl + href);
            } else if(StringUtils.startsWith(href, "http")) {
                resultLinks.add(href);
            }
        }
        return resultLinks;
    }
}