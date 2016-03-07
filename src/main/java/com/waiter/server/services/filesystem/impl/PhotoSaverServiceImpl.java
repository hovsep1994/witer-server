package com.waiter.server.services.filesystem.impl;

import com.waiter.server.services.filesystem.PhotoSaverService;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author by Shahen
 *         on 2/3/15.
 */
@Service
public class PhotoSaverServiceImpl implements PhotoSaverService {

    private static final Logger LOG = Logger.getLogger(PhotoSaverServiceImpl.class);
    private static final String UPLOAD_DIRECTORY = "/Users/shahenpoghosyan/static/";
    private static final String PATH = "http://localhost:8080/photos/";
    private static final String ORIGINALS_PATH = "originals";
    private static final String DELIMITER_DOT = ".";
    private static final int[] DEFAULT_SIZES = {60, 120};

    public PhotoSaverServiceImpl() {
        File uploadDir = new File(UPLOAD_DIRECTORY);
        createDirIfNotExist(uploadDir);
    }

    @Override
    public String savePhoto(InputStream stream, String path, int[] sizes) throws IOException {
        BufferedImage origImage = ImageIO.read(stream);
        store(origImage, path);
        for (int size : sizes) {
            resizeAndStore(origImage, path, size);
            File sizeDir = new File(new StringBuilder(UPLOAD_DIRECTORY)
                    .append(size).append(File.separator).toString());
            createDirIfNotExist(sizeDir);
        }
        return new StringBuilder().append(PATH).append(path).toString();
    }

    @Override
    public String savePhoto(InputStream stream, String path) throws IOException {
        return savePhoto(stream, path, DEFAULT_SIZES);
    }

    private void resizeAndStore(BufferedImage origImage, String path, int size) throws IOException {
        store(resizePhoto(size, origImage), path);
    }

    private void store(BufferedImage origImage, String path) throws IOException {
        String extension = FilenameUtils.getExtension(path);
        String fullPath = new StringBuilder()
                .append(UPLOAD_DIRECTORY)
                .append(ORIGINALS_PATH)
                .append(File.separator).append(path).toString();
        ImageIO.write(origImage, extension, new File(fullPath));
    }

    private BufferedImage resizePhoto(int newSize, BufferedImage image) {
        int newHeight = image.getHeight() <= image.getWidth() ? newSize : (int) ((float) image.getHeight() / image.getWidth() * newSize);
        int newWidth = image.getWidth() <= image.getHeight() ? newSize : (int) ((float) image.getWidth() / image.getHeight() * newSize);
        LOG.debug("old size: " + image.getHeight() + ", " + image.getWidth());
        LOG.debug("new size: " + newHeight + ", " + newWidth);
        return Scalr.resize(image, Scalr.Method.AUTOMATIC, newWidth, newHeight);
    }

    private void createDirIfNotExist(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
