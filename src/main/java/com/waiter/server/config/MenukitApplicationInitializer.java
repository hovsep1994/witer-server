package com.waiter.server.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author shahenpoghosyan
 */
public class MenukitApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.getServletRegistration("home").getMappings();

    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{DataAccessConfig.class, WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/api"};
    }
}
