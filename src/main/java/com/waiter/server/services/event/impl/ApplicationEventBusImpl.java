package com.waiter.server.services.event.impl;

import com.waiter.server.services.event.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hovsep on 8/5/16.
 */
@Service
public class ApplicationEventBusImpl implements ApplicationEventBus {

    private List<ApplicationEventListener> listeners;

    public ApplicationEventBusImpl() {
        listeners = new ArrayList<>();
    }

    @Override
    public void publish(ApplicationEvent event) {
        listeners.forEach(listener -> {
            listener.process(event);
        });
    }

    @Override
    public void subscribe(ApplicationEventListener applicationEventListener) {
        listeners.add(applicationEventListener);
    }

}
