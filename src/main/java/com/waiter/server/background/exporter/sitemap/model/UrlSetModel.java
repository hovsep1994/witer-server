package com.waiter.server.background.exporter.sitemap.model;

import javax.xml.bind.annotation.XmlAttribute;
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

    @XmlAttribute
    private String xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd";

    public List<UrlModel> getUrl() {
        if (url == null) {
            url = new ArrayList<>();
        }
        return url;
    }
}
