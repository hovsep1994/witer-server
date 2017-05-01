package com.waiter.server.background.exporter.sitemap;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.background.exporter.sitemap.model.UrlModel;
import com.waiter.server.background.exporter.sitemap.model.UrlSetModel;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Company: SFL LLC
 * Created on 4/29/17
 *
 * @author Hovsep Harutyunyan
 */
@Component
@Transactional
public class SitemapGenerator {

    private static final String CHARFREQ = "monthly";

    @Value("#{appProperties['sitemap.file.path']}")
    private String filePath;

    @Autowired
    private VenueService venueService;

    public void processGeneration() {
        final UrlSetModel urlSetModel = new UrlSetModel();
        urlSetModel.getUrl().add(new UrlModel("http://menuk.it", CHARFREQ));
        urlSetModel.getUrl().add(new UrlModel("http://menuk.it/business/landing", CHARFREQ));
        venueService.getVenues(0, 1000).forEach(venue ->
                venue.getMenu().getLanguages().forEach(language ->
                        urlSetModel.getUrl().add(
                                new UrlModel(getUrlFromVenue(venue, language.name()), CHARFREQ)
                        )));
        generateFile(urlSetModel);
    }

    private void generateFile(UrlSetModel urlSetModel) {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(UrlSetModel.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(urlSetModel, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String getUrlFromVenue(Venue venue, String language) {
        String name = venue.getName().replace(" ", "-");
        return "http://menuk.it/v/" + name + "/" + venue.getId() + "/" + language;
    }

    public static void main(String... args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        final SitemapGenerator sitemapGenerator = (SitemapGenerator) context.getBean("sitemapGenerator");
        sitemapGenerator.processGeneration();
    }

}
