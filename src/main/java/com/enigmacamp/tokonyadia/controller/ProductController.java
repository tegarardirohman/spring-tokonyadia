package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.ProductRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PRODUCT_API)
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> createNewProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.create(payload);

        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "New product added!", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    // /api/product?name=laptop
    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getAllProduct(
            @RequestParam(name = "name", required = false) String name
    ) {
        List<ProductResponse> productList = productService.getAll(name);
        System.out.println("IM IN PRODUCT");

        CommonResponse<List<ProductResponse>> response = CommonResponse.<List<ProductResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All product data")
                .data(Optional.of(productList))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // /api/product/{UUID}
    public ResponseEntity<CommonResponse<ProductResponse>> getProductById(@PathVariable String id) {
        ProductResponse productResponse = productService.getById(id);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "", Optional.of(productResponse));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ProductResponse>> updateProduct(@RequestBody ProductRequest payload) {
        ProductResponse product = productService.updatePut(payload);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "Product Updated", Optional.of(product));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> deleteProductById(@PathVariable String id) {
        productService.deleteById(id);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "Success Delete Product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    @PatchMapping()
    public ResponseEntity<CommonResponse<ProductResponse>> updateStock(@RequestBody ProductRequest payload) {
        productService.updatePatch(payload);
        CommonResponse<ProductResponse> response = generateProductResponse(HttpStatus.OK.value(), "Success update stock product by id", Optional.empty());
        return ResponseEntity.ok(response);
    }

    private CommonResponse<ProductResponse> generateProductResponse(Integer code, String message, Optional<ProductResponse> productResponse){
        return CommonResponse.<ProductResponse>builder()
                .statusCode(code)
                .message(message)
                .data(productResponse)
                .build();


    }
}
