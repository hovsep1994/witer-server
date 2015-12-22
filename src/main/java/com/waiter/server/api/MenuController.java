package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.commons.entities.Menu;
import com.waiter.server.services.menu.MenuService;
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

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Menu menu) {
        menuService.create(menu);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Menu> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.get(id));
    }

}
