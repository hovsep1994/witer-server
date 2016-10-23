package com.waiter.server.services.event.impl;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventBus;
import com.waiter.server.services.event.ApplicationEventListener;
import com.waiter.server.services.utility.PersistenceUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 8/5/16.
 */
@Service
public class ApplicationEventBusImpl implements ApplicationEventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEventBusImpl.class);

    private static final int MAX_THREAD_POOL_SIZE = 20;

    private static final int CORE_THREAD_POOL_SIZE = 10;

    private List<ApplicationEventListener> listeners;

    private ExecutorService executorService;

    @Autowired
    private PersistenceUtilityService persistenceUtilityService;

    public ApplicationEventBusImpl() {
        listeners = new ArrayList<>();
        initExecutorService();
    }

    @Override
    public void subscribe(ApplicationEventListener applicationEventListener) {
        listeners.add(applicationEventListener);
    }

    @Override
    public void publishSynchronousEvent(ApplicationEvent applicationEvent) {
        assertApplicationEventNotNull(applicationEvent);
        processApplicationEvent(applicationEvent);
    }

    @Override
    public void publishAsynchronousEvent(ApplicationEvent applicationEvent) {
        assertApplicationEventNotNull(applicationEvent);
        LOGGER.debug("Processing Synchronous event - {}", applicationEvent);
        final Runnable runnable = () -> persistenceUtilityService.runInNewTransaction(
                () -> processApplicationEvent(applicationEvent)
        );
        executorService.submit(runnable);
    }

    private void assertApplicationEventNotNull(ApplicationEvent applicationEvent) {
        notNull(applicationEvent);
    }

    private void initExecutorService() {
        final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_THREAD_POOL_SIZE);
        threadPoolExecutor.setCorePoolSize(CORE_THREAD_POOL_SIZE);
        threadPoolExecutor.setMaximumPoolSize(MAX_THREAD_POOL_SIZE);
        this.executorService = threadPoolExecutor;
    }

    private void processApplicationEvent(ApplicationEvent applicationEvent) {
        listeners.forEach(listener -> {
            try {
                listener.process(applicationEvent);
            } catch (Exception e) {
                LOGGER.error("Error processing application event. ", e);
            }
        });
    }

}
