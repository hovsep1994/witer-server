package com.waiter.server.persistence.core.repository.category;

import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByIdAndNameSet_language(Long id, Language language);
}