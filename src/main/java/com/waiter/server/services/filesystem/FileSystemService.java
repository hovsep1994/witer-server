package com.waiter.server.services.filesystem;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author shahenpoghosyan
 */
@Service
public interface FileSystemService {

    String saveImage(String name, InputStream stream, int[] sizes) throws IOException;
}
