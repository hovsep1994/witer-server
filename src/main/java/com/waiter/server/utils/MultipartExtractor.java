package com.waiter.server.utils;

import com.waiter.server.commons.APIError;
import com.waiter.server.commons.APIException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.waiter.server.commons.ErrorCodes.MULTIPART_NEEDED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * @author by Shahen
 *         on 2/3/15.
 */
public class MultipartExtractor {

    private static final Logger LOG = Logger.getLogger(MultipartExtractor.class);
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 10; // 10MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    private ServletFileUpload upload;
    private List<FileItem> photos;
    private HttpServletRequest request;
    private Map<String, String> fields;

    public MultipartExtractor(HttpServletRequest request) throws APIException {
        if (!ServletFileUpload.isMultipartContent(request))
            throw new APIException(SC_BAD_REQUEST, new APIError(MULTIPART_NEEDED, "Request does not contain upload data"));
        this.request = request;
        init();
    }

    private void init() throws APIException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        photos = new LinkedList<>();
        fields = new HashMap<>(10);

        try {
            List<FileItem> formItems = upload.parseRequest(request);
            Iterator<FileItem> iter = formItems.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField()) {
                    photos.add(item);
                } else {
                    fields.put(item.getFieldName(), item.getString());
                }
            }
        } catch (FileUploadException fue) {
            LOG.error("Error parsing multipart request. ", fue);
            throw new APIException(SC_BAD_REQUEST, new APIError(MULTIPART_NEEDED, "Request does not contain upload data"));
        }
    }

    public String savePhoto(String name) throws IOException {
        PhotoSaver saver = new PhotoSaver(photos.get(0), name);
        return saver.savePhoto();
    }

    public String get(String key) {
        return fields.get(key);
    }
}
