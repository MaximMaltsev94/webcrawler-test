package com.maltsau.maksim.webcrawler.termsanalyzer;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HTMLBodyTermsAnalyzer implements TermsAnalyzer{
    private TermsAnalyzer plainTextTermsAnalyzer;

    @Autowired
    @Qualifier("plaintextTermsAnalyzer")
    public void setPlainTextTermsAnalyzer(TermsAnalyzer plainTextTermsAnalyzer) {
        this.plainTextTermsAnalyzer = plainTextTermsAnalyzer;
    }

    @Override
    public Long analyzeMatchesCount(String source, String term) {
        String htmlBodyText = Jsoup.parse(source).body().text();
        return plainTextTermsAnalyzer.analyzeMatchesCount(htmlBodyText, term);
    }
}
