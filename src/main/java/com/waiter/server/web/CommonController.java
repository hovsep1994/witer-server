package com.waiter.server.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Company: SFL LLC
 * Created on 4/28/17
 *
 * @author Hovsep Harutyunyan
 */
@RestController
@RequestMapping("/")
public class CommonController {

    @Value("#{appProperties['sitemap.file.path']}")
    private String filePath;

    @RequestMapping(value = "c66a90755855.html", method = RequestMethod.GET)
    public String businessLanding(ModelMap model) {
        return "baf8ed6bad08";
    }

    @RequestMapping(value = "sitemap", method = RequestMethod.GET)
    public FileSystemResource getFile(HttpServletResponse response) throws IOException {
        return new FileSystemResource(filePath);
    }

}
