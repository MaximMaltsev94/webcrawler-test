package com.maltsau.maksim.webcrawler.termsanalyzer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PlaintextTermsAnalyzer implements TermsAnalyzer {
    //TODO tests
    @Override
    public Long analyzeMatchesCount(String source, String term) {
        return (long) StringUtils.countMatches(source, term);
    }
}