package com.waiter.server.api.menu;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.menu.model.MenuModel;
import com.waiter.server.api.menu.model.request.MenuRequest;
import com.waiter.server.api.menu.model.response.MenuWithProductsModel;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import com.waiter.server.services.user.model.User;
import com.waiter.server.services.venue.VenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/12/2015.
 */
@RestController
@RequestMapping("/menus")
public class MenuController extends AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getCompanyMenus(@ModelAttribute("user") User user) {
        List<Menu> menus = menuService.getMenusByCompanyId(user.getCompany().getId());
        List<MenuModel> menuModels = menus.stream().map(MenuModel::convert).collect(Collectors.toList());
        return MenuKitResponseEntity.success(menuModels);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
    public ResponseEntity getMenu(@PathVariable Long menuId, @RequestParam Language language) {
        final Menu menu = menuService.getById(menuId);
        if (language == null) {
            language = menu.getMainLanguage();
        }
        final MenuWithProductsModel model = MenuWithProductsModel.convert(menu, language);
        return MenuKitResponseEntity.success(model);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addMenu(@RequestBody MenuRequest request, @ModelAttribute User user) {
        final MenuDto menuDto = MenuRequest.convert(request);
        final Menu menu = menuService.create(user.getCompany().getId(), menuDto);
        if (request.getVenues() != null) {
            venueService.attacheMenuToVenues(request.getVenues(), menu.getId());
        }
        final MenuModel menuModel = MenuModel.convert(menu);
        return MenuKitResponseEntity.success(menuModel);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.PUT)
    public ResponseEntity updateMenu(@PathVariable Long menuId, @RequestBody MenuRequest request) {
        final MenuDto menuDto = MenuRequest.convert(request);
        final Menu menu = menuService.update(menuId, menuDto);
        if (request.getVenues() != null) {
            venueService.attacheMenuToVenues(request.getVenues(), menu.getId());
        }
        final MenuModel menuModel = MenuModel.convert(menu);
        return MenuKitResponseEntity.success(menuModel);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMenu(@PathVariable Long menuId, @ModelAttribute User user) {
        final Menu menu = menuService.getById(menuId);
        checkUserHasAccess(user, menu.getCompany());
        menuService.delete(menuId);
        return MenuKitResponseEntity.success();
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity heartbeat() {
        return MenuKitResponseEntity.success("ok");
    }

}
