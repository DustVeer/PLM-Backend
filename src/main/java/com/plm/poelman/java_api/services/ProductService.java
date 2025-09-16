package com.plm.poelman.java_api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plm.poelman.java_api.models.ProductCategory;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.repositories.ProductCategoryRepository;
import com.plm.poelman.java_api.repositories.ProductRepository;
import com.plm.poelman.java_api.repositories.ProductStatusRepository;
import com.plm.poelman.java_api.repositories.UserRepository;

@Service
public class ProductService {

    private final ProductRepository _productRepository;
    private final ProductCategoryRepository _categoryRepository;
    private final UserRepository _userRepository;
    private final ProductStatusRepository _statusRepository;

    public ProductService(ProductRepository productRepository,
            ProductCategoryRepository categoryRepository,
            UserRepository userRepository,
            ProductStatusRepository statusRepository) {
        this._productRepository = productRepository;
        this._categoryRepository = categoryRepository;
        this._userRepository = userRepository;
        this._statusRepository = statusRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return _productRepository.findById(id)
                .map(product -> {
                    ProductCategory category = _categoryRepository.findById(product.getCategoryId()).orElse(null);
                    User createdBy = _userRepository.findById(product.getCreatedBy()).orElse(null);
                    ProductStatus status = _statusRepository.findById(product.getStatusId()).orElse(null);

                    return new ProductResponse(product, category, createdBy, status);
                })
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductsResponse() {
        return _productRepository.findAll().stream()
                .map(product -> {
                    ProductCategory category = _categoryRepository.findById(product.getCategoryId()).orElse(null);
                    User createdBy = _userRepository.findById(product.getCreatedBy()).orElse(null);
                    ProductStatus status = _statusRepository.findById(product.getStatusId()).orElse(null);

                    return new ProductResponse(product, category, createdBy, status);
                }).toList();

    }
}
