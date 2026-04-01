package org.iimsa.product_service.presentation.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.application.dto.command.UpdateProductCommand;
import org.iimsa.product_service.application.service.ProductService;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.presentation.dto.request.CreateProductRequestDto;
import org.iimsa.product_service.presentation.dto.request.UpdateProductRequestDto;
import org.iimsa.product_service.presentation.dto.response.CreateProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.FindProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.UpdateProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public FindProductResponseDto findById(@PathVariable("id") UUID productId) {
        Product product =  productService.getProduct(productId);
        return FindProductResponseDto.from(product);
    }

    @PatchMapping("/{id}")
    public UpdateProductResponseDto updateById(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {

        // 1. DTO -> Command 변환
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);

        // 2. 서비스 호출 (내부에서 조회 -> 권한체크 -> 부분수정 수행)
        productService.updateProduct(id, command);

        // 3. 응답 DTO 생성 및 반환
        return new UpdateProductResponseDto(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
