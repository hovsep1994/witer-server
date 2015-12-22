package com.waiter.server.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author by Shahen
 *         on 2/3/15.
 */
public class PhotoSaver {

    private static final Logger LOG = Logger.getLogger(PhotoSaver.class);
    private static final String UPLOAD_DIRECTORY = "/Users/shahenpoghosyan/static/";
    private static final String PATH = "http://localhost:8080/photos/";
    private static final String ORIGINALS_PATH = "originals";
    private static final String DELIMITER_DOT = ".";
    private static final int[] DEFAULT_SIZES = {60, 120};

    static {
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        for (int size : DEFAULT_SIZES) {
            File sizeDir = new File(new StringBuilder(UPLOAD_DIRECTORY).append(size)
                    .append(File.separator).toString());
            if (!sizeDir.exists()) {
                sizeDir.mkdir();
            }
        }
    }

    private String path;
    private String extension;
    private BufferedImage origImage;

    public PhotoSaver(FileItem photo, String name) throws IOException {
        this(photo.getInputStream(), name);
    }

    public PhotoSaver(InputStream stream, String path) throws IOException {
        this.path = path;
        this.extension = FilenameUtils.getExtension(path);
        this.origImage = ImageIO.read(stream);
    }

    public String savePhoto(int[] sizes) throws IOException {
        try {
            storeOriginal();
            for (int size : sizes) {
                resizeAndStore(size);
            }
        } catch (Exception e) {
            LOG.error("Error when storing photo: ", e);
            throw new IOException(e);
        }
        return new StringBuilder().append(PATH).append(path).toString();
    }

    public String savePhoto() throws IOException {
        return savePhoto(DEFAULT_SIZES);
    }

    private void resizeAndStore(int size) throws IOException {
        String path = new StringBuilder().append(UPLOAD_DIRECTORY).append(size)
                .append(this.path).toString();

        BufferedImage resizedImage = resizePhoto(size, origImage);
        ImageIO.write(resizedImage, extension, new File(path));
    }

    private void storeOriginal() throws IOException {
        String fullPath = new StringBuilder().append(UPLOAD_DIRECTORY).append(ORIGINALS_PATH)
                .append(File.separator).append(this.path).toString();
        ImageIO.write(origImage, extension, new File(fullPath));
    }

    private BufferedImage resizePhoto(int newSize, BufferedImage image) {
        int newHeight = image.getHeight() <= image.getWidth() ? newSize : (int) ((float) image.getHeight() / image.getWidth() * newSize);
        int newWidth = image.getWidth() <= image.getHeight() ? newSize : (int) ((float) image.getWidth() / image.getHeight() * newSize);
        LOG.debug("old size: " + image.getHeight() + ", " + image.getWidth());
        LOG.debug("new size: " + newHeight + ", " + newWidth);
        return Scalr.resize(image, Scalr.Method.AUTOMATIC, newWidth, newHeight);
    }
}
