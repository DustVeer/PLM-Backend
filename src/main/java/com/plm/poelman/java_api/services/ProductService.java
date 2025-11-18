package com.plm.poelman.java_api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.ProductCategory;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.products.UpdateProductRequest;
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
    public boolean existsByCategoryId(Long categoryId) {
        return _categoryRepository.existsById(categoryId);
    }

    @Transactional(readOnly = true)
    public boolean existsByStatusId(Long statusId) {
        return _statusRepository.existsById(statusId);
    }

    @Transactional(readOnly = true)
    public Boolean existsById(Long id) {
        return _productRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserId(Long createdBy) {
        return _userRepository.existsById(createdBy);
    }

    @Transactional
    public Product createProduct(Product req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setCategoryId(req.getCategoryId());
        product.setStatusId(req.getStatusId());
        product.setCreatedBy(req.getCreatedBy());

        return _productRepository.save(product);

    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest req) {

        Product existingProduct = _productRepository.findById(id).orElse(null);
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product();
        product.setId(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setCategoryId(req.getCategoryId());
        product.setStatusId(req.getStatusId());
        product.setUpdatedBy(req.getUpdatedBy());
        product.setCreatedBy(existingProduct.getCreatedBy());
        product.setUpdatedAt(now);

        _productRepository.save(product);
        ProductResponse dto = this.getProductById(id);

        return dto;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return _productRepository.findById(id)
                .map(product -> {
                    ProductCategory category = _categoryRepository.findById(product.getCategoryId()).orElse(null);
                    UserResponse createdBy = new UserResponse(
                            _userRepository.findById(product.getCreatedBy()).orElse(null));
                    UserResponse updatedBy = new UserResponse(
                            _userRepository.findById(product.getUpdatedBy()).orElse(null));
                    ProductStatus status = _statusRepository.findById(product.getStatusId()).orElse(null);

                    return new ProductResponse(product, category, createdBy, updatedBy, status);
                })
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductsResponse() {
        return _productRepository.findAll().stream()
                .map(product -> {
                    ProductCategory category = _categoryRepository.findById(product.getCategoryId()).orElse(null);
                    UserResponse createdBy = new UserResponse(
                            _userRepository.findById(product.getCreatedBy()).orElse(null));
                    UserResponse updatedBy = new UserResponse(
                            _userRepository.findById(product.getUpdatedBy()).orElse(null));
                    ProductStatus status = _statusRepository.findById(product.getStatusId()).orElse(null);

                    return new ProductResponse(product, category, createdBy, updatedBy, status);
                }).toList();

    }

}
