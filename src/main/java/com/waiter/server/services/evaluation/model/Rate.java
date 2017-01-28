package com.waiter.server.services.evaluation.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;

/**
 * Created by hovsep on 3/12/16.
 */
@Entity
@Table(name = "rate")
public class Rate extends AbstractEntityModel {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Column(name = "customer_token", nullable = false)
    private String customerToken;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Rate rate = (Rate) o;

        if (evaluation != null ? !evaluation.equals(rate.evaluation) : rate.evaluation != null) return false;
        if (customerToken != null ? !customerToken.equals(rate.customerToken) : rate.customerToken != null)
            return false;
        return rating != null ? rating.equals(rate.rating) : rate.rating == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (evaluation != null ? evaluation.hashCode() : 0);
        result = 31 * result + (customerToken != null ? customerToken.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "evaluation=" + evaluation +
                ", customerToken='" + customerToken + '\'' +
                ", rating=" + rating +
                '}';
    }
}
