package com.waiter.server.services.evaluation.impl;

import com.waiter.server.persistence.core.repository.evaluation.RateRepository;
import com.waiter.server.services.evaluation.EvaluationService;
import com.waiter.server.services.evaluation.RateService;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.evaluation.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by hovsep on 3/12/16.
 */
@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private EvaluationService evaluationService;

    @Override
    public Rate create(Long evaluationId, String customerToken, Integer rating) {
        final Evaluation evaluation = evaluationService.getById(evaluationId);

        final Rate rate = new Rate();
        rate.setRating(rating);
        rate.setCustomerToken(customerToken);
        rate.setEvaluation(evaluation);
        return rateRepository.save(rate);
    }

    @Override
    public Rate update(Long evaluationId, String customerToken, Integer rating) {
        Rate rate = find(evaluationId, customerToken);
        Assert.notNull(rate);

        rate.setRating(rating);
        return rateRepository.save(rate);
    }

    @Override
    public Rate find(Long evaluationId, String customerToken) {
        return  rateRepository.findByCustomerTokenAndEvaluationId(customerToken, evaluationId);
    }
}