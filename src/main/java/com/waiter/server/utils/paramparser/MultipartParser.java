package com.waiter.server.utils.paramparser;

import com.waiter.server.commons.APIException;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Shahen
 *         on 2/4/15.
 */
public class MultipartParser extends AbstractParamParser {

    private MultipartExtractor extractor;

    public MultipartParser(HttpServletRequest request) throws APIException {
        extractor = new MultipartExtractor(request);
    }

    public MultipartParser(MultipartExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public String get(String key) {
        return extractor.get(key);
    }

    @Override
    public boolean isFileExists() {
        return extractor.getFile() != null;
    }

    @Override
    public FileItem getFile() {
        return extractor.getFile();
    }
}
