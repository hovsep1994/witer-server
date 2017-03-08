package com.waiter.server.services.category.impl;

import com.waiter.server.persistence.core.repository.category.CategoryRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.gallery.GalleryImageService;
import com.waiter.server.services.gallery.dto.GalleryImageDto;
import com.waiter.server.services.gallery.model.Gallery;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.gallery.model.GalleryImageType;
import com.waiter.server.services.gallery.model.ImageType;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.tag.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * @author shahenpoghosyan
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private GalleryImageService galleryImageService;

    @Override
    public Category getById(Long id) {
        assertCategoryId(id);
        final Category category = categoryRepository.findOne(id);
        if (category == null) {
            LOGGER.error("category with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Category not found");
        }
        return category;
    }

    @Override
    @Transactional
    public Category create(CategoryDto categoryDto, Long menuId) {
        assertCategoryDto(categoryDto);
        notNull(menuId);
        final Menu menu = menuService.addLanguage(menuId, categoryDto.getLanguage());
        final Category category = new Category();
        category.setGallery(new Gallery());
        category.setMenu(menu);
        categoryDto.updateProperties(category);
        final Set<Tag> tags = tagService.create(categoryDto.getTags());
        category.setTags(tags);
        final TranslationDto translationDto = new TranslationDto(categoryDto.getName(), categoryDto.getLanguage());
        final Translation translation = translationService.create(translationDto);
        category.getNameSet().add(translation);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Long categoryId, CategoryDto categoryDto) {
        assertCategoryId(categoryId);
        assertCategoryDto(categoryDto);
        final Category category = getById(categoryId);
        categoryDto.updateProperties(category);
        if (category.getTags() != null) {
            final Set<Tag> tags = tagService.create(categoryDto.getTags());
            category.setTags(tags);
        }
        final TranslationDto translationDto = new TranslationDto(categoryDto.getName(), categoryDto.getLanguage());
        final Translation translation = translationService.create(translationDto);
        category.getNameSet().add(translation);
        category.setUpdated(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public void remove(Long categoryId) {
        assertCategoryId(categoryId);
        final Category category = getById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public Category addOrUpdateTranslation(Long categoryId, TranslationDto nameDto) {
        assertCategoryId(categoryId);
        final Category category = getById(categoryId);
        if (nameDto != null) {
            Translation name = category.getNameTranslationByLanguage(nameDto.getLanguage());
            if (name == null) {
                name = translationService.create(nameDto);
            } else {
                name = translationService.update(name.getId(), nameDto);
            }
            category.getNameSet().add(name);
        }
        return categoryRepository.save(category);
    }

    @Override
    public GalleryImage addImage(Long categoryId, InputStream inputStream) throws ServiceException {
        Category category = getById(categoryId);
        GalleryImageDto galleryImageDto = new GalleryImageDto();
        galleryImageDto.setGalleryImageType(GalleryImageType.MAIN);
        galleryImageDto.setImageType(ImageType.JPEG);
        galleryImageDto.setFileName("category");
        if (category.getGallery() == null) {
            category.setGallery(new Gallery());
            category = categoryRepository.save(category);
        }
        return galleryImageService.addImage(category.getGallery().getId(), galleryImageDto, inputStream);
    }

    @Override
    public Category getByIdAndLanguage(Long id, Language language) {
        assertCategoryId(id);
        notNull(language);
        final Category category = getCategoryOrNullByIdAndLanguage(id, language);
        if (category == null) {
            LOGGER.error("category with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Category not found");
        }
        return category;
    }

    @Override
    public Category getCategoryOrNullByIdAndLanguage(Long id, Language language) {
        assertCategoryId(id);
        notNull(language);
        return categoryRepository.findByIdAndNameSet_language(id, language);
    }

    @Override
    public Company getCompanyById(Long id) {
        assertCategoryId(id);
        return getById(id).getMenu().getCompany();
    }

    private static void assertCategoryId(Long id) {
        notNull(id, "category id must not be null");
    }

    private static void assertCategoryDto(CategoryDto categoryDto) {
        notNull(categoryDto, "category dto must not be null");
    }

}
