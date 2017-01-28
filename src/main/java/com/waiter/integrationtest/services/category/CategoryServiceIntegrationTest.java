package com.waiter.integrationtest.services.category;

import com.waiter.integrationtest.test.AbstractServiceIntegrationTest;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.menu.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hovsep on 9/17/16.
 */
public class CategoryServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() {
        final Menu menu = getTestHelperService().createMenu();
        final CategoryDto categoryDto = getTestHelperService().createCategoryDto();
        final Category category = categoryService.create(categoryDto, menu.getId());
        assertEquals(menu, category.getMenu());
        assertTrue(category.getLanguages().contains(categoryDto.getLanguage()));
        category.getTags().forEach(tag -> assertTrue(categoryDto.getTags().contains(tag.getName())));
        assertEquals(categoryDto.getName(), category.getNameTranslationByLanguage(categoryDto.getLanguage()).getText());
    }

}
