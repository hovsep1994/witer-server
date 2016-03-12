package com.waiter.server.services.evaluation.impl;

import com.waiter.server.persistence.core.repository.evaluation.EvaluationRepository;
import com.waiter.server.services.evaluation.EvaluationService;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.evaluation.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by hovsep on 3/12/16.
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    public Evaluation getRating(Long id) {
        assertEvaluationId(id);
        return evaluationRepository.findOne(id);
    }

    @Override
    public Evaluation addRating(Long id, Rate rate) {
        assertEvaluationId(id);
        Evaluation evaluation = evaluationRepository.findOne(id);
        evaluation.getRates().add(rate);
        return evaluationRepository.save(evaluation);
    }

    private void assertEvaluationId(Long id) {
        Assert.notNull(id, "Evaluation id must not be null");
    }

}

