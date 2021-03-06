package com.waiter.server.persistence.core.repository.name;

import com.waiter.server.services.translation.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

}
