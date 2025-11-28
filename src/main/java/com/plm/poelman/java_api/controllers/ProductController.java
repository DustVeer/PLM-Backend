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
import com.plm.poelman.java_api.models.dto.errors.StatusChangeError;
import com.plm.poelman.java_api.models.dto.products.CreateUpdateProductRequest;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.products.SmallProductResponse;
import com.plm.poelman.java_api.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService _productService;

    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    @GetMapping
    public List<SmallProductResponse> getAllProducts() {

        return _productService.getAllSmallProductsResponse();
    }

    

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {

        return _productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody CreateUpdateProductRequest req) {

        if (req == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body is missing" );
        }
        if(req.getName() == null || req.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is missing" );
        }
        if(!_productService.existsByUserId(req.getCreatedById())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CreatedBy UserId does not exist" );
        }

        LocalDateTime now = LocalDateTime.now();
        req.setCreatedAt(now);
        req.setUpdatedAt(now);

        System.out.println("Creating product with name: " + req.getName());

        ProductResponse dto = _productService.createProduct(req);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody CreateUpdateProductRequest req) {


        if (req == null || id == 0 || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body and Id are missing" );
        }
        if(!_productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist" );
        }
       
        ProductResponse updated = _productService.updateProduct(id, req);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update product" );
            
        }

        return ResponseEntity.ok(updated);

    }

    @PutMapping("/{id}/status/{statusId}")
    public ResponseEntity<?> updateProductStatus(@PathVariable Long id, @PathVariable Long statusId) {
        if (id == null || statusId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id and StatusId are missing" );
        }
        if(!_productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist" );
        }
        if(!_productService.existsByStatusId(statusId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("StatusId does not exist" );
        }
        List<String> missingFields = _productService.allowedStatusChangeByID(id);
        if(!missingFields.isEmpty()) {
            StatusChangeError error = new StatusChangeError("Cannot change to the requested status. Missing required fields.", missingFields);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        ProductResponse updated = _productService.changeProductStatus(id, statusId);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update product status");
            
        }

        return ResponseEntity.ok(updated);
    }

}
