package com.waiter.server.api.category;

import com.waiter.server.api.common.model.ResponseEntity;
import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.category.model.request.AddCategoryModelRequest;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CategoryModel> getCategoryById(@PathVariable Long id, @RequestParam Language language) {
        Category category = categoryService.getById(id);
        CategoryModel categoryModel = CategoryModel.convert(category, language);
        return ResponseEntity.forResponse(categoryModel);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<CategoryModel> addCategory(@RequestBody AddCategoryModelRequest addCategoryModelRequest) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setLanguage(addCategoryModelRequest.getAddNameTranslationRequest().getLanguage());
        categoryDto.setGroupName(addCategoryModelRequest.getAddNameTranslationRequest().getName());
        categoryDto.setTags(TagModel.convert(addCategoryModelRequest.getTagModels()));
        Category category = categoryService.create(addCategoryModelRequest.getMenuId(), categoryDto);
        CategoryModel categoryModel = CategoryModel.convert(category, addCategoryModelRequest.getAddNameTranslationRequest().getLanguage());
        return ResponseEntity.forResponse(categoryModel);
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.forResponse("ok");
    }

}
