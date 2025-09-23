package com.plm.poelman.java_api.controllers;

import java.net.http.HttpResponse.ResponseInfo;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.dto.products.ProductResponse;
import com.plm.poelman.java_api.models.dto.products.UpdateProductRequest;
import com.plm.poelman.java_api.services.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService _productService;

    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {

        return _productService.getAllProductsResponse();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProductById(@PathVariable Long id, @RequestBody UpdateProductRequest req) {

        if (req == null || id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body and Id are missing" );
        }
        if(!_productService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id does not exist" );
        }
       
        Product updated = _productService.updateProduct(id, req);

        return ResponseEntity.ok(updated);

    }

}
