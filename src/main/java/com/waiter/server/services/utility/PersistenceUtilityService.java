package com.waiter.server.services.utility;

/**
 * Created by hovsep on 8/7/16.
 */
public interface PersistenceUtilityService {

    void runInNewTransaction(Runnable runnable);

}
