package com.waiter.server.services.evaluation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by hovsep on 3/12/16.
 */
@Entity
@Table(name = "evaluation")
public class Evaluation extends AbstractEntityModel {

    @OneToMany(mappedBy = "evaluation",fetch = FetchType.LAZY)
    private Set<Rate> rates;

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }
}
