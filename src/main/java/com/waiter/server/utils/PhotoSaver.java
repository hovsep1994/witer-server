package com.waiter.server.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

/**
 * @author by Shahen
 *         on 2/3/15.
 */
public class PhotoSaver {

    private static final Logger LOG = Logger.getLogger(PhotoSaver.class);
    private static final String UPLOAD_DIRECTORY = "/home/shahen/web/upload/";
    private static final String PATH = "http://localhost:8080/photos/";
    private static final String ORIGINALS_PATH = "originals";
    private static final String DELIMITER_DOT = ".";
    private static final int[] DEFAULT_SIZES = {60,120};
    static {
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }
    private FileItem photo;
    private String name;
    private String extension;
    private BufferedImage origImage;

    public PhotoSaver(FileItem photo, String name) throws IOException {
        this.photo = photo;
        this.name = name;
        this.extension = FilenameUtils.getExtension(new File(photo.getName()).getName());
        this.origImage = ImageIO.read(photo.getInputStream());
        LOG.debug("test: " + origImage.getWidth());
    }

    public String savePhoto() throws IOException {
        try {
            storeOriginal();
            for(int size : DEFAULT_SIZES ) {
                resizeAndStore(size);
            }
        } catch (Exception e) {
            LOG.error("Error when storing photo: ", e);
            throw new IOException(e);
        }
        return new StringBuilder().append(PATH).append(name).append(DELIMITER_DOT)
                .append(extension).toString();
    }

    private void resizeAndStore(int size) throws IOException {
        String path = new StringBuilder().append(UPLOAD_DIRECTORY).append(size)
                .append(File.separator).append(name).append(DELIMITER_DOT)
                .append(extension).toString();

        BufferedImage resizedImage = resizePhoto(size, origImage);
        ImageIO.write(resizedImage, extension, new File(path));
    }

    private void storeOriginal() throws IOException {
        String path = new StringBuilder().append(UPLOAD_DIRECTORY).append(ORIGINALS_PATH)
                .append(File.separator).append(name).append(DELIMITER_DOT)
                .append(extension).toString();
        LOG.debug("test: " + path + ", " + origImage.getHeight());
        ImageIO.write(origImage, extension, new File(path));
        LOG.debug("test2: " );
    }

    private BufferedImage resizePhoto(int newSize, BufferedImage image) {
        int newHeight = image.getHeight() <= image.getWidth() ? newSize : (int)( (float)image.getHeight() / image.getWidth() * newSize);
        int newWidth = image.getWidth() <= image.getHeight() ? newSize : (int)( (float)image.getWidth() / image.getHeight() * newSize);
        LOG.debug("old size: " + image.getHeight() + ", " + image.getWidth() );
        LOG.debug("new size: " + newHeight + ", " + newWidth);
        return Scalr.resize(image, Scalr.Method.AUTOMATIC, newWidth, newHeight, new BufferedImageOp[0]);
    }
}
