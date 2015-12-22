package com.waiter.server.services.filesystem.impl;

import com.waiter.server.services.filesystem.FileSystemService;
import com.waiter.server.utils.PhotoSaver;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
public class FileSystemServiceImpl implements FileSystemService {

    @Override
    public String saveImage(String name, InputStream stream, int[] sizes) throws IOException {
        PhotoSaver saver = new PhotoSaver(stream, name); //todo handle exception properly
        return saver.savePhoto(sizes);
    }
}
