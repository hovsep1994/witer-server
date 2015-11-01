package com.waiter.server.utils.paramparser;

import com.waiter.server.commons.APIException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shahenpoghosyan
 */
public class ParserFactory {

    public IParamParser newParser(HttpServletRequest request) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            try {
                return new MultipartParser(request);
            } catch (APIException e) {
                //ignore
            }
        }
        return new BaseParser(request);
    }
}
