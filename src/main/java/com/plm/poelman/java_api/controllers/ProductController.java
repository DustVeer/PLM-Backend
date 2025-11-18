package com.plm.poelman.java_api.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.products.UpdateProductRequest;
import com.plm.poelman.java_api.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService _productService;

    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {

        return _productService.getAllProductsResponse();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {

        return _productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody Product req) {

        if (req == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body is missing" );
        }
        if(req.getName() == null || req.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is missing" );
        }
        if(req.getDescription() == null || req.getDescription().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Description is missing" );
        }
        if(req.getCategoryId() == null || req.getCategoryId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CategoryId is missing" );
        }
        if(req.getStatusId() == null || req.getStatusId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("StatusId is missing" );
        }
        if(req.getCreatedBy() == null || req.getCreatedBy() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CreatedBy is missing" );
        }
        if(!_productService.existsByCategoryId(req.getCategoryId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CategoryId does not exist" );
        }
        if(!_productService.existsByStatusId(req.getStatusId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("StatusId does not exist" );
        }
        if(!_productService.existsByUserId(req.getCreatedBy())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CreatedBy UserId does not exist" );
        }

        LocalDateTime now = LocalDateTime.now();
        req.setCreatedAt(now);
        req.setUpdatedAt(now);

        Product created = _productService.createProduct(req);

        created.setCreatedAt(now);
        created.setUpdatedAt(now);

        return ResponseEntity.ok(created);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProductById(@PathVariable Long id, @RequestBody UpdateProductRequest req) {

        System.out.println("Received PUT request for ID: " + id + " with body: " + req.getDescription());

        if (req == null || id == 0 || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body and Id are missing" );
        }
        if(!_productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist" );
        }
       
        ProductResponse updated = _productService.updateProduct(id, req);

        return ResponseEntity.ok(updated);

    }

}
