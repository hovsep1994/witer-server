package com.waiter.server.api;

import com.waiter.server.services.gallery.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

//    @RequestMapping(value = "/products/{productId}", method = RequestMethod.POST)
//    public ResponseEntity<Void> saveProductImage(@PathVariable Long productId,
//                                                 @RequestHeader("Content-Type") String contentType,
//                                                 InputStream stream) throws IOException {
//        String extension = contentType.substring(6); //todo throw error if not valid
//        galleryService.save(new Gallery(productId, extension), stream);
//        return new ResponseEntity<>();
//    }
}