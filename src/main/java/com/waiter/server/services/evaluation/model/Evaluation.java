package com.waiter.server.services.evaluation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by hovsep on 3/12/16.
 */
@Entity
@Table(name = "evaluation")
public class Evaluation extends AbstractEntityModel {

    @OneToMany(mappedBy = "evaluation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rate> rates;

    @Column(name = "rate_sum")
    private long rateSum;

    @Column(name = "rate_count")
    private long rateCount;

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public long getRateSum() {
        return rateSum;
    }

    public void setRateSum(long rateSum) {
        this.rateSum = rateSum;
    }

    public long getRateCount() {
        return rateCount;
    }

    public void setRateCount(long rateCount) {
        this.rateCount = rateCount;
    }

    public Double getAverageRating() {
        if(getRateCount() == 0) {
            return 0d;
        }
        return (double) getRateSum() / (double) getRateCount();
    }
}
