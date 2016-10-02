package com.waiter.server.background.services;

import com.waiter.server.background.conf.ApplicationConf;
import com.waiter.server.services.venue.VenueService;
import com.waiter.server.services.venue.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author shahenpoghosyan
 */
@Service
public class MenuImportService {

    @Autowired
    VenueService venueService;

    public Venue getVenue(Long id) {
        return venueService.getById(id);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConf.class);
        MenuImportService menuImportService = (MenuImportService) ctx.getBean("menuImportService");
        menuImportService.getVenue(1L);

    }

}
