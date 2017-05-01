package com.waiter.server.background.exporter.sitemap.model;

/**
 * Company: SFL LLC
 * Created on 4/29/17
 *
 * @author Hovsep Harutyunyan
 */
public class UrlModel {
    private String loc;
    private String changefreq;

    public UrlModel(String loc, String changefreq) {
        this.loc = loc;
        this.changefreq = changefreq;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }
}
