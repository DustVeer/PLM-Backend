package com.plm.poelman.java_api.services;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyleContext.SmallAttributeSet;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.RequiredField;
import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.models.dto.workflows.WorkflowResponse;
import com.plm.poelman.java_api.models.dto.categories.CategoryResponse;
import com.plm.poelman.java_api.models.dto.products.CreateUpdateProductRequest;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.products.SmallProductResponse;
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
    public List<String> allowedStatusChangeByID(Long ProductID) {
        Product product = _productRepository.findById(ProductID).orElse(null);
        ProductStatus currentStatus = product.getStatus();
        List<String> missingFields = new ArrayList<>();

        try {
            for (RequiredField rf : currentStatus.getRequiredFields()) {

                String fieldKey = rf.getFieldKey();
                String fieldKeyFirstLower = Character.toLowerCase(fieldKey.charAt(0)) + fieldKey.substring(1);


                Field field = Product.class.getDeclaredField(fieldKeyFirstLower);
                
                field.setAccessible(true);
                Object value = field.get(product);

                if (value == null || (value instanceof String && ((String) value).isBlank())) {
                    missingFields.add(fieldKeyFirstLower);
                }
            }
            return missingFields;
        }   
        catch (Exception e) {
            throw new RuntimeException("Required field validation failed", e);
        }
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
    public ProductResponse createProduct(CreateUpdateProductRequest req) {
        User createdBy = _userRepository.findById(req.getCreatedById()).orElse(null);
        Product product = new Product();
        product.setName(req.getName());
        product.setCreatedBy(createdBy);
        product.setUpdatedBy(createdBy);

        System.out.println("Creating Product: " + product.getName() + ", CreatedBy: " + createdBy.getName());

        Product created = _productRepository.save(product);

        LocalDateTime now = LocalDateTime.now();

        created.setCreatedAt(now);
        created.setUpdatedAt(now);

        return new ProductResponse(created);
    }

    public ProductResponse updateProduct(Long id, CreateUpdateProductRequest req) {

        System.out.println("productId      = " + id);
        System.out.println("categoryId     = " + req.getCategoryId());
        System.out.println("statusId       = " + req.getStatusId());
        System.out.println("updatedById    = " + req.getUpdatedById());

        Product existingProduct = _productRepository.findById(id).orElse(null);

        LocalDateTime now = LocalDateTime.now();
        Product product = new Product();
        product.setId(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setColour(req.getColour());
        product.setPrice(req.getPrice());
        product.setCategory(_categoryRepository.findById(req.getCategoryId()).orElse(null));
        product.setStatus(_statusRepository.findById(req.getStatusId()).orElse(null));
        product.setUpdatedBy(_userRepository.findById(req.getUpdatedById()).orElse(null));
        product.setCreatedBy(existingProduct.getCreatedBy());
        product.setCreatedAt(existingProduct.getCreatedAt());
        product.setUpdatedAt(now);
        product.setWorkflow(existingProduct.getWorkflow());

        _productRepository.save(product);
        ProductResponse dto = this.getProductById(id);

        System.out.println("Updated Product: " + dto.getName() + ", Description: " + dto.getDescription()
                + ", UpdatedAt: " + dto.getCreatedAt());

        return dto;
    }

    @Transactional
    public ProductResponse changeProductStatus(Long id, Long statusId) {
        Product existingProduct = _productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }

        ProductStatus newStatus = _statusRepository.findById(statusId).orElse(null);
        if (newStatus == null) {
            return null;
        }

        existingProduct.setStatus(newStatus);
        existingProduct.setUpdatedAt(LocalDateTime.now());
        Product updatedProduct = _productRepository.save(existingProduct);

        return this.mapToProductResponse(updatedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return _productRepository.findById(id)
                .map(this::mapToProductResponse)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<SmallProductResponse> getAllSmallProductsResponse() {
        return _productRepository.findAll().stream()
                .map(this::mapToSmallProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        CategoryResponse category = new CategoryResponse(product.getCategory());
        UserResponse createdBy = new UserResponse(product.getCreatedBy());
        UserResponse updatedBy = new UserResponse(product.getUpdatedBy());
        WorkflowResponse workflow = new WorkflowResponse(product.getWorkflow(),
                new UserResponse(product.getWorkflow().getCreatedBy()),
                new UserResponse(product.getWorkflow().getUpdatedBy()),
                product.getWorkflow().getWorkflowStatuses().stream()
                        .map(ws -> new StatusResponse(ws.getStatus()))
                        .toList());
        StatusResponse status = new StatusResponse(product.getStatus());

        return new ProductResponse(product, category, createdBy, updatedBy, status, workflow);
    }

    private SmallProductResponse mapToSmallProductResponse(Product product) {
        if (product.getStatus() == null) {
            return new SmallProductResponse(product);
        }
        return new SmallProductResponse(product, product.getStatus());
    }

   
}