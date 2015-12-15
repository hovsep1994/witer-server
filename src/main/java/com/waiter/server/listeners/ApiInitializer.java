package com.waiter.server.listeners;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by shahen on 11/19/14.
 */
@WebListener
public class ApiInitializer implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ApiInitializer.class);
    private ApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
        } catch (Exception e) {
            LOG.error("Error when starting app. ", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
