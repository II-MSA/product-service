package org.iimsa.product_service.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Product API", description = "상품 관리 및 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    @Operation(summary = "상품 생성", description = "새로운 상품을 등록합니다. (MASTER, HUB_MANAGER, COMPANY_MANAGER 권한 필요)")
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto requestDto) {
        CreateProductCommand command = new CreateProductCommand(
                requestDto.productName(),
                requestDto.companyId()
        );
        UUID productId = productService.createProduct(command);
        return new CreateProductResponseDto(productId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 단건 조회", description = "상품 ID를 통해 특정 상품의 상세 정보를 조회합니다.")
    public FindProductResponseDto getProduct(
            @Parameter(description = "상품 식별자(UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("id") UUID productId) {
        Product product = productService.getProduct(productId);
        return FindProductResponseDto.from(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    @Operation(summary = "상품 전체 수정", description = "상품의 모든 정보를 수정합니다.")
    public UpdateProductResponseDto updateProduct(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);
        productService.updateProduct(id, command);
        return new UpdateProductResponseDto(id);
    }

    @PatchMapping("/{id}/productName")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    @Operation(summary = "상품명 수정", description = "상품의 이름만 부분적으로 수정합니다.")
    public UpdateProductResponseDto updateProductName(
            @PathVariable("id") UUID id,
            @RequestBody UpdateProductRequestDto requestDto) {
        UpdateProductCommand command = UpdateProductCommand.from(requestDto);
        productService.updateProductName(id, command);
        return new UpdateProductResponseDto(id);
    }

    @PatchMapping("/{id}/associate")
    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
    @Operation(summary = "상품 소속 수정", description = "상품이 속한 업체(Company) 정보를 수정합니다.")
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
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다. (MASTER, HUB_MANAGER 권한 필요)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    public void deleteProduct(@PathVariable("id") UUID productId) {
        productService.deleteProductById(productId);
    }

    @GetMapping("/search")
    @Operation(summary = "상품 목록 검색/페이징", description = "페이징 처리된 상품 목록을 조회합니다.")
    public Page<ListProductResponseDto> search(
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return productService.searchProducts(pageable);
    }
}
