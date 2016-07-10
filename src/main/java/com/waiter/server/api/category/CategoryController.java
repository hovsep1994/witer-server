package com.waiter.server.api.category;

import com.waiter.server.api.category.model.request.UpdateCategoryRequest;
import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.category.model.request.AddCategoryModelRequest;
import com.waiter.server.api.name.model.NameTranslationModel;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.translation.dto.TranslationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable Long id, @RequestParam Language language) {
        Category category = categoryService.getByIdAndLanguage(id, language);
        CategoryModel categoryModel = CategoryModel.convert(category, language);
        return ResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<CategoryModel> addCategory(@RequestBody AddCategoryModelRequest request) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        TranslationDto translationDto = NameTranslationModel.convert(request.getNameTranslationModel());
        Category category = categoryService.create(request.getMenuId(), categoryDto, translationDto);
        CategoryModel categoryModel = CategoryModel.convert(category, translationDto.getLanguage());
        return ResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        TranslationDto translationDto = NameTranslationModel.convert(request.getNameTranslation());
        Category category = categoryService.update(id, categoryDto, translationDto);
        CategoryModel categoryModel = CategoryModel.convert(category, translationDto.getLanguage());
        return ResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.success("ok");
    }

}
