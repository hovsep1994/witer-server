package com.waiter.server.solr.core.repository.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by hovsep on 7/31/16.
 */
public abstract class AbstractSolrDocument implements Serializable {

    private static final long serialVersionUID = 217644385607351932L;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
