package com.waiter.integrationtest.test;

import com.waiter.integrationtest.services.help.TestHelperService;
import com.waiter.server.config.DataAccessConfig;
import com.waiter.server.config.MenuKitApplicationInitializer;
import com.waiter.server.config.RootConfig;
import com.waiter.server.config.SolrConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by hovsep on 9/17/16.
 */
@ContextConfiguration(classes = {RootConfig.class, DataAccessConfig.class, SolrConfig.class})
public class AbstractServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceIntegrationTest.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TestHelperService testHelperService;

    void flush() {
        entityManager.flush();
    }

    void clear() {
        entityManager.clear();
    }

    void flushAndClear() {
        flush();
        clear();
    }

    public TestHelperService getTestHelperService() {
        return testHelperService;
    }
}
