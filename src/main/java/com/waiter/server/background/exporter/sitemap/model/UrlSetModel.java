package com.waiter.server.background.exporter.sitemap.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 4/29/17
 *
 * @author Hovsep Harutyunyan
 */
@XmlRootElement(name = "urlset")
public class UrlSetModel {

    @XmlElement
    private List<UrlModel> url;

    public List<UrlModel> getUrl() {
        if (url == null) {
            url = new ArrayList<>();
        }
        return url;
    }
}
