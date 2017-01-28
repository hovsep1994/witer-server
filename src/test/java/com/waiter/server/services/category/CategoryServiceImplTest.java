package com.waiter.server.services.category;

import com.waiter.server.persistence.core.repository.category.CategoryRepository;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.impl.CategoryServiceImpl;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.tag.TagService;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * Created by hovsep on 9/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService = new CategoryServiceImpl();

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagService tagService;

    @Mock
    private MenuService menuService;

    @Mock
    private TranslationService translationService;

    @Test
    public void testCreateCategory() {
        final Long menuId = 2L;
        final Language language = Language.en;
        final CategoryDto categoryDto = new CategoryDto();
        final Set<String> tags = new HashSet<>();
        final TranslationDto translationDto = new TranslationDto();
        final Category category = new Category();

        when(menuService.addLanguage(menuId, language)).thenReturn(new Menu());
        when(tagService.create(tags)).thenReturn(new HashSet<>());
        when(translationService.create(translationDto)).thenReturn(new Translation());
        when(categoryRepository.save(isA(Category.class))).thenReturn(category);
        Category result = categoryService.create(categoryDto, 2L);
        assertEquals(category, result);
    }


}
