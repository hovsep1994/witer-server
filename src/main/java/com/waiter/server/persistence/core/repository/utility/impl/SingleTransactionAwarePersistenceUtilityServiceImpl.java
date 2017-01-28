package com.waiter.server.persistence.core.repository.utility.impl;

public class SingleTransactionAwarePersistenceUtilityServiceImpl extends PersistenceUtilityServiceImpl {

    /* Constructors */
    public SingleTransactionAwarePersistenceUtilityServiceImpl() {
    }

    @Override
    public void runInNewTransaction(final Runnable runnable) {
        runnable.run();
    }

    @Override
    public void runAfterTransactionCommitIsSuccessful(final Runnable runnable, final boolean executeAsynchronously) {
        runnable.run();
    }
}
