package com.waiter.server.api.category;

import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.category.model.request.AddCategoryModelRequest;
import com.waiter.server.api.category.model.request.UpdateCategoryRequest;
import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MenuKitResponseEntity<CategoryModel> getCategoryById(@PathVariable Long id, @RequestParam Language language) {
        Category category = categoryService.getByIdAndLanguage(id, language);
        CategoryModel categoryModel = CategoryModel.convert(category, language);
        return MenuKitResponseEntity.success2(categoryModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public MenuKitResponseEntity<CategoryModel> addCategory(@RequestBody AddCategoryModelRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, menuService.getCompanyByMenuId(request.getMenuId()));
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        final TranslationDto translationDto = NameTranslationModel.convert(request.getNameTranslationModel());
        final Category category = categoryService.create(request.getMenuId(), categoryDto, translationDto);
        final CategoryModel categoryModel = CategoryModel.convert(category, translationDto.getLanguage());
        return MenuKitResponseEntity.success2(categoryModel);
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public MenuKitResponseEntity<CategoryModel> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, categoryService.getCompanyById(id));
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        TranslationDto translationDto = NameTranslationModel.convert(request.getNameTranslation());
        Category category = categoryService.update(id, categoryDto, translationDto);
        CategoryModel categoryModel = CategoryModel.convert(category, translationDto.getLanguage());
        return MenuKitResponseEntity.success2(categoryModel);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public MenuKitResponseEntity<String> heartbeat() {
        return MenuKitResponseEntity.success2("ok");
    }

}
