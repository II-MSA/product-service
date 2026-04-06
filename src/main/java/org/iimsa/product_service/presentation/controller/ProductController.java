package org.iimsa.product_service.presentation.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.product_service.application.dto.command.CreateProductCommand;
import org.iimsa.product_service.application.dto.command.UpdateProductCommand;
import org.iimsa.product_service.application.service.ProductService;
import org.iimsa.product_service.domain.model.Product;
import org.iimsa.product_service.presentation.dto.request.CreateProductRequestDto;
import org.iimsa.product_service.presentation.dto.request.UpdateProductRequestDto;
import org.iimsa.product_service.presentation.dto.response.CreateProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.FindProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.ListProductResponseDto;
import org.iimsa.product_service.presentation.dto.response.UpdateProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto requestDto
    ) {
        CreateProductCommand command = new CreateProductCommand(
                requestDto.productName(),
                requestDto.companyId()
        );

        UUID productId = productService.createProduct(command);

        return new CreateProductResponseDto(productId);
    }

    @GetMapping("/{id}")
    public FindProductResponseDto getProduct(@PathVariable("id") UUID productId) {
        Product product = productService.getProduct(productId);
        return FindProductResponseDto.from(product);
    }

    // 상품 정보 전체 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    public UpdateProductResponseDto updateProduct(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);

        productService.updateProduct(id, command);
        return new UpdateProductResponseDto(id);
    }

    // 상품명만 수정
    @PatchMapping("{id}/productName")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    public UpdateProductResponseDto updateProductName(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);

        productService.updateProductName(id, command);
        return new UpdateProductResponseDto(id);
    }

    // 상품의 소속만 수정
    @PatchMapping("{id}/associate")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    public UpdateProductResponseDto updateAssociate(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);

        productService.updateAssociate(id, command);
        return new UpdateProductResponseDto(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    public void deleteProduct(@PathVariable("id") UUID productId) {
        productService.deleteProductById(productId);
    }

    @GetMapping("/search")
    public Page<ListProductResponseDto> search(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productService.searchProducts(pageable);
    }

}
