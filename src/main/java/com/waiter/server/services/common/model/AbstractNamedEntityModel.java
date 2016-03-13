package com.waiter.server.services.common.model;

import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.NameTranslation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@MappedSuperclass
public abstract class AbstractNamedEntityModel extends AbstractEntityModel {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NameTranslation> nameTranslations;

    public AbstractNamedEntityModel() {
        nameTranslations = new ArrayList<>();
    }

    public List<NameTranslation> getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(List<NameTranslation> nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public NameTranslation getNameTranslationByLanguage(Language language) {
        return nameTranslations.stream().filter(nameTranslation ->
                nameTranslation.getLanguage().equals(language)).findFirst().orElse(null);
    }
}
