package com.waiter.server.services.evaluation;

import com.waiter.server.services.evaluation.model.Rate;

/**
 * Created by hovsep on 3/12/16.
 */
public interface RateService {

    Rate create(Long evaluationId, String customerToken, Integer rating);

    Rate update(Long evaluationId, String customerToken, Integer rating);

    Rate find(Long evaluationId, String customerToken);
}
