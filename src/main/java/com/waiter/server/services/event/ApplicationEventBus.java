package com.waiter.server.services.event;

/**
 * Created by hovsep on 8/5/16.
 */
public interface ApplicationEventBus {

    void publish(ApplicationEvent event);

    void subscribe(ApplicationEventListener applicationEventListener);

}
