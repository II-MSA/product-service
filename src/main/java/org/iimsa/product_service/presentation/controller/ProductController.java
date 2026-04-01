package org.iimsa.product_service.presentation.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.application.service.ProductService;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.presentation.dto.request.CreateProductRequestDto;
import org.iimsa.product_service.presentation.dto.response.CreateProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.FindProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProductResponseDto save(CreateProductRequestDto requestDto) {

        UUID productId = productService.createProduct(requestDto.productName(), requestDto.companyId());

        return new CreateProductResponseDto(productId);
    }

    @GetMapping("{id}")
    public FindProductResponseDto findById(@PathVariable UUID productId) {
        Product product =  productService.getProduct(productId);
        return FindProductResponseDto.from(product);
    }
}
