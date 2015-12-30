package com.waiter.server.persistence.core.repository.menu;

import com.waiter.server.services.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCompanyId(Long companyId);
}
