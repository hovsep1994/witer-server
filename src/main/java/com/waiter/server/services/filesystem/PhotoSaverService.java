package com.waiter.server.services.filesystem;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hovsep on 3/6/16.
 */
public interface PhotoSaverService {

    String savePhoto(InputStream stream, String path, int[] sizes) throws IOException;

    String savePhoto(InputStream stream, String path) throws IOException;

}
