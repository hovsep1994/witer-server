package com.waiter.server.listeners;

import org.apache.log4j.Logger;
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
            ServletContext servletContext = event.getServletContext();
            context = new ClassPathXmlApplicationContext("spring-config.xml");
            servletContext.setAttribute("springContext", context);
        } catch (Exception e) {
            LOG.error("Error when starting app. ", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
