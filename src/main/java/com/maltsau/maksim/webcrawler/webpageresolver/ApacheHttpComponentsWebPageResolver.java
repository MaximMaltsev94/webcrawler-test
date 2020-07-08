package com.maltsau.maksim.webcrawler.webpageresolver;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApacheHttpComponentsWebPageResolver implements WebPageResolver {
    private Logger LOG = LoggerFactory.getLogger(ApacheHttpComponentsWebPageResolver.class);

    private HttpClient httpClient;

    public ApacheHttpComponentsWebPageResolver() {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public String getPageContent(String uri) {
        try {

            HttpGet getPageRequest = new HttpGet(uri);
            HttpResponse response = httpClient.execute(getPageRequest);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            LOG.error(String.format("error while getting page `%s`", uri), e);
            throw new RuntimeException(e);
        }

    }
}
