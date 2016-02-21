package com.waiter.server.services.name;

import com.waiter.server.services.name.model.NameTranslation;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface NameService {
    NameTranslation create(NameTranslation nameTranslation);
    List<NameTranslation> create(List<NameTranslation> nameTranslations);
}
