package com.waiter.server.api.menu;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.menu.model.MenuModel;
import com.waiter.server.api.menu.model.request.AddMenuRequest;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private HibernateEntityManagerFactory hibernateEntityManagerFactory;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MenuModel> findOne(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        MenuModel menuModel = MenuModel.convert(menu);
        return ResponseEntity.forResponse(menuModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<MenuModel> addMenu(@RequestBody AddMenuRequest addMenuRequest) {
        Menu createdMenu = menuService.create(addMenuRequest.getName(), addMenuRequest.getCompanyId());
        MenuModel menuModel = MenuModel.convert(createdMenu);
        return ResponseEntity.forResponse(menuModel);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List> getCompanyMenus(@RequestParam("companyId") Long companyId) {
        List<Menu> menus = menuService.getMenusByCompanyId(companyId);
        List<MenuModel> menuModels = new ArrayList<>(menus.size());
        menus.stream().forEach(menu -> menuModels.add(MenuModel.convert(menu)));
        return ResponseEntity.forResponse(menuModels);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.forResponse("ok");
    }

}
