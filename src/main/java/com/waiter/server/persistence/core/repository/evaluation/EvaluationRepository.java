package com.waiter.server.persistence.core.repository.evaluation;

import com.waiter.server.services.evaluation.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hovsep on 3/12/16.
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
