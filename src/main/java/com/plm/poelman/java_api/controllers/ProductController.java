package com.plm.poelman.java_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.dto.products.ProductResponse;
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

}
