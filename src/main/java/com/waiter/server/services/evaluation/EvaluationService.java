package com.waiter.server.services.evaluation;

import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.evaluation.model.Rate;

/**
 * Created by hovsep on 3/12/16.
 */
public interface EvaluationService {

    Evaluation getRating(Long id);

    Evaluation addRating(Long id, Rate rate);

}
