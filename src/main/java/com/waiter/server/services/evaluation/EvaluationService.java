package com.waiter.server.services.evaluation;

import com.waiter.server.services.evaluation.model.Evaluation;

/**
 * Created by hovsep on 3/12/16.
 */
public interface EvaluationService {

    Evaluation getById(Long id);

    Evaluation addOrUpdateRating(Long evaluationId, String customerToken, Integer rating, RateMode rateMode);

    enum RateMode {
        OVERRIDE, ADD
    }
}
