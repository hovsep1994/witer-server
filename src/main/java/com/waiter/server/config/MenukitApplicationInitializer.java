package com.waiter.server.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author shahenpoghosyan
 */
public class MenukitApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{DataAccessConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/api"};
    }
}
