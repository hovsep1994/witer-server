package com.waiter.server.services.utility.impl;

import com.waiter.server.services.utility.PersistenceUtilityService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.util.Assert.notNull;

/**
 * Created by hovsep on 8/7/16.
 */
@Service
public class PersistenceUtilityServiceImpl implements PersistenceUtilityService {

    private static final Logger LOGGER = Logger.getLogger(PersistenceUtilityServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void runInNewTransaction(final Runnable runnable) {
        notNull(runnable);
        runnable.run();
    }

}
