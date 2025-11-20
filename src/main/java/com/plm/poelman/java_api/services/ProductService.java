package com.plm.poelman.java_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.models.dto.workflows.WorkflowResponse;
import com.plm.poelman.java_api.models.dto.categories.CategoryResponse;
import com.plm.poelman.java_api.models.dto.products.CreateUpdateProductRequest;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
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
    public boolean existsById(Long id) {
        return _productRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByUserId(Long createdBy) {
        return _userRepository.existsById(createdBy);
    }

    @Transactional
    public Product createProduct(CreateUpdateProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setCategory(_categoryRepository.findById(req.getCategoryId()).orElse(null));
        product.setStatus(_statusRepository.findById(req.getStatusId()).orElse(null));
        product.setCreatedBy(_userRepository.findById(req.getCreatedById()).orElse(null));

        return _productRepository.save(product);

    }

    public ProductResponse updateProduct(Long id, CreateUpdateProductRequest req) {

        Product existingProduct = _productRepository.findById(id).orElse(null);
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product();
        product.setId(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setCategory(_categoryRepository.findById(req.getCategoryId()).orElse(null));
        product.setStatus(_statusRepository.findById(req.getStatusId()).orElse(null));
        product.setUpdatedBy(_userRepository.findById(req.getUpdatedById()).orElse(null));
        product.setCreatedBy(existingProduct.getCreatedBy());
        product.setCreatedAt(existingProduct.getCreatedAt());
        product.setUpdatedAt(now);

        _productRepository.save(product);
        ProductResponse dto = this.getProductById(id);

        System.out.println("Updated Product: " + dto.getName() + ", Description: " + dto.getDescription() + ", UpdatedAt: " + dto.getCreatedAt() );

        return dto;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return _productRepository.findById(id)
                .map(this::mapToProductResponse)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductsResponse() {
        return _productRepository.findAll().stream()
                .map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        CategoryResponse category = new CategoryResponse(product.getCategory());
        UserResponse createdBy = new UserResponse(product.getCreatedBy());
        UserResponse updatedBy = new UserResponse(product.getUpdatedBy());
        WorkflowResponse workflow = new WorkflowResponse(product.getWorkflow(), new UserResponse(product.getWorkflow().getCreatedBy()) , new UserResponse(product.getWorkflow().getUpdatedBy()), 
                product.getWorkflow().getWorkflowStatuses().stream()
                .map(ws -> new StatusResponse(ws.getStatus()))
                .toList());
        StatusResponse status = new StatusResponse(product.getStatus());
    

        return new ProductResponse(product, category, createdBy, updatedBy, status, workflow);
    }
}