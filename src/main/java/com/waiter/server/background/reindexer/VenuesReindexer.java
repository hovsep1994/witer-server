package com.waiter.server.background.reindexer;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.services.venue.VenueSearchService;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Component
@Transactional
public class VenuesReindexer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsReindexer.class);

    @Autowired
    private VenueService venueService;
    @Autowired
    private VenueSearchService venueSearchService;

    public void reindexAll() {
        int i = 0;
        List<Venue> venues;
        while (!(venues = venueService.getVenues(i++, 1000)).isEmpty()) {

            venues.forEach(venue -> {
                try {
                    venueSearchService.addOrUpdate(venue);
                } catch (Exception e) {
                    LOGGER.error("Error saving venue in solr. ", e);
                }
            });
            LOGGER.info("Venues batch was indexed. ");
        }
    }


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class);
        VenuesReindexer reindexer = context.getBean("venuesReindexer", VenuesReindexer.class);
        reindexer.reindexAll();
    }
}
