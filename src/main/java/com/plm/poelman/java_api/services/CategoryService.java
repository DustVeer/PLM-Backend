package com.plm.poelman.java_api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plm.poelman.java_api.models.ProductCategory;
import com.plm.poelman.java_api.models.dto.categories.CategoryResponse;
import com.plm.poelman.java_api.models.dto.categories.CategorySearchResponse;
import com.plm.poelman.java_api.repositories.ProductCategoryRepository;

@Service
public class CategoryService {

    private final ProductCategoryRepository _productCategoryRepository;

    public CategoryService(ProductCategoryRepository productCategoryRepository) {
        this._productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public CategorySearchResponse searchCategoriesByName(String searchString, int page, int pageSize) {
        // Frontend uses 1-based pages → JPA uses 0-based
        int pageIndex = Math.max(page - 1, 0);

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if (searchString == null || searchString.isBlank()) {
            return mapToCategorySearchResponse(_productCategoryRepository.findAll(pageable));
        }

        return mapToCategorySearchResponse(
                _productCategoryRepository.findByNameContainingIgnoreCase(searchString, pageable));
    }

    private CategoryResponse mapToCategoryResponse(ProductCategory category) {
        return new CategoryResponse(category);
    }

    private CategorySearchResponse mapToCategorySearchResponse(Page<ProductCategory> categoryPage) {
        List<CategoryResponse> categoryResponses = categoryPage.stream()
                .map(this::mapToCategoryResponse)
                .toList();

        return new CategorySearchResponse(
                categoryResponses,
                categoryPage.getNumber() + 1,
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast());
    }
}
