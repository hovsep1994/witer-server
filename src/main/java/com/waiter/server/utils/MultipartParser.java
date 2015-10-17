package com.waiter.server.utils;

/**
 * @author by Shahen
 *         on 2/4/15.
 */
public class MultipartParser extends AbstractParamParser {

    private MultipartExtractor extractor;

    public MultipartParser(MultipartExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public String get(String key) {
        return extractor.get(key);
    }
}
