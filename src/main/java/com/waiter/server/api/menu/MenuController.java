package com.waiter.server.api.menu;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.api.menu.model.request.AddMenuRequest;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Menu> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.get(id));
    }

    @RequestMapping(value = "/add}", method = RequestMethod.POST)
    public ResponseEntity<Menu> createMenu(@RequestBody AddMenuRequest addMenuRequest) {
        Menu menu = new Menu();
        Menu createdMenu = menuService.create(menu);
        return new ResponseEntity<>(createdMenu);
    }
}
