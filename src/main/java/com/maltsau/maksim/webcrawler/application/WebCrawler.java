package com.maltsau.maksim.webcrawler.application;

import com.maltsau.maksim.webcrawler.domain.CrawlResponse;

import java.util.List;

public interface WebCrawler {
    CrawlResponse crawlWebSite(String url, List<String> termsList);

    List<CrawlResponse> crawlWebSiteRecursively(String url, List<String> termsList, Integer linkDepth, Integer pagesLimit);
}
