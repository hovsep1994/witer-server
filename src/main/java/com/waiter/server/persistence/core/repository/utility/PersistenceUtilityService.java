package com.waiter.server.persistence.core.repository.utility;

/**
 * Created by hovsep on 7/5/16.
 */
public interface PersistenceUtilityService {

    <T> T initializeAndUnProxy(T entity);

    void runInPersistenceSession(Runnable runnable);

    void runInNewTransaction(Runnable runnable);

    void runInTransaction(Runnable runnable);

    void runAfterTransactionCommitIsSuccessful(Runnable runnable, boolean executeAsynchronously);
}
