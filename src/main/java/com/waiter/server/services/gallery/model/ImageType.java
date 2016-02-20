package com.waiter.server.services.gallery.model;

import org.springframework.util.Assert;

/**
 * Created by Admin on 12/24/2015.
 */
public enum ImageType {
    JPEG("image/jpeg"), PNG("image/png"), GIF("image/gif"), BMP("image/bmp"), WEBP("image/webp"), OTHER("image/other");

    private final String mimeMediaType;

    private ImageType(final String mimeMediaType) {
        this.mimeMediaType = mimeMediaType;
    }

    public String getMimeMediaType() {
        return mimeMediaType;
    }

    public static ImageType identifyImageType(final String output) {
        Assert.notNull(output);
        // Determine image type
        final ImageType retVal;
        final String outputCompareValue = output.toLowerCase().trim();
        if (outputCompareValue.contains("jpeg") || outputCompareValue.contains("jpg")) {
            retVal = ImageType.JPEG;
        } else if (outputCompareValue.contains("png")) {
            retVal = ImageType.PNG;
        } else if (outputCompareValue.contains("bmp")) {
            retVal = ImageType.BMP;
        } else if (outputCompareValue.contains("webp")) {
            retVal = ImageType.WEBP;
        } else if (outputCompareValue.contains("gif")) {
            retVal = ImageType.GIF;
        } else {
            retVal = ImageType.OTHER;
        }
        return retVal;
    }
}
