package com.waiter.server.services.evaluation.impl;

import com.waiter.server.persistence.core.repository.evaluation.EvaluationRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.evaluation.EvaluationService;
import com.waiter.server.services.evaluation.RateService;
import com.waiter.server.services.evaluation.model.Evaluation;
import com.waiter.server.services.evaluation.model.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by hovsep on 3/12/16.
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private RateService rateService;

    @Override
    public Evaluation getById(Long id) {
        final Evaluation evaluation = evaluationRepository.findOne(id);
        if (evaluation == null) {
            LOGGER.error("evaluation with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "evaluation not found");
        }
        return evaluation;
    }

    @Override
    @Transactional
    public Evaluation addOrUpdateRating(Long evaluationId, String customerToken, Integer rating, RateMode rateMode) {
        Evaluation evaluation = getById(evaluationId);

        if (rateMode == RateMode.ADD) {
            rateService.create(evaluation.getId(), customerToken, rating);
            evaluation.setRateSum(evaluation.getRateSum() + rating);
            evaluation.setRateCount(evaluation.getRateCount() + 1);
        } else {
            Rate rate = rateService.find(evaluationId, customerToken);
            if (rate != null) {
                evaluation.setRateSum(evaluation.getRateSum() + rating - rate.getRating());
                rateService.update(evaluationId, customerToken, rating);
            } else {
                rateService.create(evaluation.getId(), customerToken, rating);
                evaluation.setRateSum(evaluation.getRateSum() + rating);
                evaluation.setRateCount(evaluation.getRateCount() + 1);

            }
        }
        return evaluationRepository.save(evaluation);
    }

    private void assertEvaluationId(Long id) {
        Assert.notNull(id, "Evaluation id must not be null");
    }

}

