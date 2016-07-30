package com.waiter.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author shahenpoghosyan
 */
public class MenuKitApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // root context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class, DataAccessConfig.class);
        rootContext.scan("com.waiter.server.services.*.impl", "com.waiter.server.externalclients");
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addFilter("openEntityManagerInViewFilter", getOpenEntityManagerInViewFilter())
                .addMappingForUrlPatterns(null, false, "/*");

        // dispatcher servlet 1
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(rootContext);
        webContext.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcherWeb = servletContext.addServlet("dispatcherWeb", new DispatcherServlet(webContext));
        dispatcherWeb.setLoadOnStartup(1);
        dispatcherWeb.addMapping("/");

        // dispatcher servlet 2
        AnnotationConfigWebApplicationContext apiContext = new AnnotationConfigWebApplicationContext();
        apiContext.setParent(rootContext);
        apiContext.register(ApiConfig.class);
        ServletRegistration.Dynamic dispatcherApi = servletContext.addServlet("dispatcherApi", new DispatcherServlet(apiContext));
        dispatcherApi.setLoadOnStartup(1);
        dispatcherApi.addMapping("/api/*");
    }

    private OpenEntityManagerInViewFilter getOpenEntityManagerInViewFilter() {
        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        openEntityManagerInViewFilter.setEntityManagerFactoryBeanName("entityManagerFactory");
        return openEntityManagerInViewFilter;
    }
}
