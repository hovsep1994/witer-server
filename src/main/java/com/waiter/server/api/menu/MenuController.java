package com.waiter.server.api.menu;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.menu.model.MenuModel;
import com.waiter.server.api.menu.model.request.AddMenuRequest;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MenuModel> findOne(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        MenuModel menuModel = MenuModel.convert(menu);
        return ResponseEntity.success(menuModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<MenuModel> addMenu(@RequestBody AddMenuRequest addMenuRequest, @ModelAttribute User user) {
        Menu menu = menuService.create(addMenuRequest.getName(), addMenuRequest.getCompanyId());
        checkUserHasAccess(user, menu.getCompany());
        MenuModel menuModel = MenuModel.convert(menu);
        return ResponseEntity.success(menuModel);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List> getCompanyMenus(@RequestParam("companyId") Long companyId) {
        List<Menu> menus = menuService.getMenusByCompanyId(companyId);
        List<MenuModel> menuModels = new ArrayList<>(menus.size());
        menus.stream().forEach(menu -> menuModels.add(MenuModel.convert(menu)));
        return ResponseEntity.success(menuModels);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.success("ok");
    }

}
