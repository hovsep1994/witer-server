package com.waiter.server.services.filesystem.impl;

import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.filesystem.PhotoSaverService;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoSaverServiceImpl.class);
    private static final int[] DEFAULT_SIZES = {};

    @Value("#{appProperties['images.directory']}")
    private String uploadDir;

    @Value("#{appProperties['images.path']}")
    private String path;

    public PhotoSaverServiceImpl() {
    }

    @Override
    public String savePhoto(InputStream stream, String filename, int[] sizes) throws IOException {
        BufferedImage origImage = ImageIO.read(stream);
        store(origImage, filename);
        for (int size : sizes) {
            resizeAndStore(origImage, filename, size);
            File sizeDir = new File(uploadDir + size + File.separator);
            createDirIfNotExist(sizeDir);
        }
        return path + filename;
    }

    @Override
    public String savePhoto(InputStream stream, String filename) throws IOException {
        return savePhoto(stream, filename, DEFAULT_SIZES);
    }

    private void resizeAndStore(BufferedImage origImage, String filename, int size) throws IOException {
        store(resizePhoto(size, origImage), filename);
    }

    private void store(BufferedImage origImage, String filename) throws IOException {
        String extension = FilenameUtils.getExtension(filename);
        String fullPath = uploadDir + File.separator + filename;
        ImageIO.write(origImage, extension, new File(fullPath));
    }

    private BufferedImage resizePhoto(int newSize, BufferedImage image) {
        int newHeight = image.getHeight() <= image.getWidth() ? newSize : (int) ((float) image.getHeight() / image.getWidth() * newSize);
        int newWidth = image.getWidth() <= image.getHeight() ? newSize : (int) ((float) image.getWidth() / image.getHeight() * newSize);
        LOGGER.debug("old size: " + image.getHeight() + ", " + image.getWidth());
        LOGGER.debug("new size: " + newHeight + ", " + newWidth);
        return Scalr.resize(image, Scalr.Method.AUTOMATIC, newWidth, newHeight);
    }

    private void createDirIfNotExist(File dir) {
        if (!dir.exists()) {
            boolean create = dir.mkdir();
            if (!create) {
                LOGGER.error("can not create direction -{}", dir);
                throw new ServiceRuntimeException(ErrorCode.CAN_NOT_CREATE_DIRECTION, "can not create direction");
            }
        }
    }
}
