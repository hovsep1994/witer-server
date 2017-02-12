package com.waiter.server.persistence.core.repository.evaluation;

import com.waiter.server.services.evaluation.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hovsep on 3/12/16.
 */
public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByCustomerTokenAndEvaluationId(String token, Long evaluationId);
}
