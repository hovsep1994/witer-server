package com.waiter.server.api.category;

import com.waiter.server.api.category.model.CategoryModel;
import com.waiter.server.api.category.model.request.AddCategoryModelRequest;
import com.waiter.server.api.category.model.request.CategoryTranslateRequest;
import com.waiter.server.api.category.model.request.UpdateCategoryRequest;
import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.tag.model.TagModel;
import com.waiter.server.api.utility.image.EntityType;
import com.waiter.server.api.utility.image.ImageUrlGenerator;
import com.waiter.server.services.category.CategoryService;
import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.gallery.model.GalleryImage;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.translation.TranslationService;
import com.waiter.server.services.translation.dto.TranslationDto;
import com.waiter.server.services.translation.model.Translation;
import com.waiter.server.services.translation.model.TranslationType;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/categories")
public class CategoryController extends AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TranslationService translationService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getCategoryById(@PathVariable Long id, @RequestParam Language language) {
        Category category = categoryService.getByIdAndLanguage(id, language);
        CategoryModel categoryModel = CategoryModel.convert(category, language);
        return MenuKitResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addCategory(@RequestBody AddCategoryModelRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, menuService.getCompanyByMenuId(request.getMenuId()));
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        final TranslationDto translationDto = new TranslationDto(request.getName(), request.getLanguage());
        final Translation translation = translationService.create(translationDto);
        final Category category = categoryService.create(categoryDto, request.getMenuId(), translation.getId());
        final CategoryModel categoryModel = CategoryModel.convert(category, translationDto.getLanguage());
        return MenuKitResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity updateCategory(@PathVariable Long categoryId, @RequestBody UpdateCategoryRequest request, @ModelAttribute User user) {
        checkUserHasAccess(user, categoryService.getCompanyById(categoryId));
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTags(TagModel.convert(request.getTagModels()));
        Translation translation = null;
        if (request.getName() != null) {
            final Category category = categoryService.getCategoryOrNullByIdAndLanguage(categoryId, request.getLanguage());
            final TranslationDto translationDto = new TranslationDto(request.getName(), request.getLanguage());
            if (category == null) {
                LOGGER.error("Translation with language -{} not found");
                throw new ServiceRuntimeException(ErrorCode.BAD_REQUEST, "Translation with language not found");
            } else {
                translation = translationService.update(category.getNameTranslationByLanguage(translationDto.getLanguage()).getId(), translationDto);
            }
        }
        Long translationId = (translation == null) ? null : translation.getId();
        Category category = categoryService.update(categoryId, categoryDto, translationId);
        CategoryModel categoryModel = CategoryModel.convert(category, request.getLanguage());
        return MenuKitResponseEntity.success(categoryModel);
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long id, @ModelAttribute User user) throws ServiceException {
        final Category category = categoryService.getById(id);
        checkUserHasAccess(user, category.getMenu().getCompany());
        final GalleryImage galleryImage;
        try {
            galleryImage = categoryService.addImage(id, file.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        }
        return MenuKitResponseEntity.success(ImageUrlGenerator.getFullUrl(EntityType.PRODUCT, galleryImage));
    }

    @RequestMapping(value = "{categoryId}/translate", method = RequestMethod.PUT)
    public ResponseEntity addTranslation(@PathVariable Long categoryId, @RequestBody CategoryTranslateRequest request, @ModelAttribute User user) {
        Category category = categoryService.getById(categoryId);
        checkUserHasAccess(user, category.getMenu().getCompany());
        if (request.getName() != null) {
            Translation name = category.getNameTranslationByLanguage(request.getLanguage());
            final TranslationDto translationDto = new TranslationDto(request.getName(), request.getLanguage());
            translationDto.setTranslationType(TranslationType.MANUAL);
            if (name != null) {
                translationService.update(name.getId(), translationDto);
            } else {
                name = translationService.create(translationDto);
                category = categoryService.update(categoryId, null, name.getId());
            }
        }
        return MenuKitResponseEntity.success(CategoryModel.convert(category, request.getLanguage()));
    }

    @RequestMapping(value = "{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCategory(@PathVariable Long categoryId, @ModelAttribute User user) {
        checkUserHasAccess(user, categoryService.getCompanyById(categoryId));
        categoryService.remove(categoryId);
        return MenuKitResponseEntity.success();
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public MenuKitResponseEntity<String> heartbeat() {
        return MenuKitResponseEntity.success2("ok");
    }

}
