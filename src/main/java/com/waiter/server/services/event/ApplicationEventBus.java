package com.waiter.server.services.event;

/**
 * Created by hovsep on 8/5/16.
 */
public interface ApplicationEventBus {

    void subscribe(ApplicationEventListener applicationEventListener);

    void publishSynchronousEvent(ApplicationEvent event);

    void publishAsynchronousEvent(ApplicationEvent applicationEvent);

}
