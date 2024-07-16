package com.enigmacamp.tokonyadia.controller;

import com.enigmacamp.tokonyadia.constant.APIUrl;
import com.enigmacamp.tokonyadia.model.dto.request.CustomerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.AvatarResponse;
import com.enigmacamp.tokonyadia.model.dto.response.CommonResponse;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.PageResponse;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.FileStorageService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.Optional;


@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
@SecurityRequirement(name = "Authorization")
public class CustomerController {
    private final CustomerService customerService;
    private final FileStorageService fileStorageService;

//    @PostMapping
//    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
//        CustomerResponse createdCustomer = customerService.createCustomer(request);
//        return ResponseEntity.ok(createdCustomer);
//    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@Validated @RequestBody CustomerRequest request) {
        CustomerResponse createdCustomer = customerService.updateCustomer(request);

        CommonResponse<CustomerResponse> commonResponse = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update Data Success")
                .data(Optional.of(createdCustomer))
                .build();

        return ResponseEntity.ok(commonResponse);

//        return ResponseEntity.ok(createdCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@Validated @PathVariable String id) {
        customerService.deleteCustomer(id);
        // block jika terjadi exception pada line 35
        return ResponseEntity.ok("Success Delete Customer By Id");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@Validated @PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/images/{img}")
    public ResponseEntity<byte[]> getImage(@PathVariable String img) {

        return fileStorageService.getImage(img);
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping
    public ResponseEntity<PageResponse<CustomerResponse>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortType", defaultValue = "ASC") String sortType,
            @RequestParam(name = "property", defaultValue = "name") String property,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "birthDate", required = false) Date birthDate
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortType), property);
        Pageable pageable = PageRequest.of(page, size, sort);

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(name)
                .phoneNumber(phone)
                .address(address)
                .birthDate(birthDate)
                .build();

        Page<CustomerResponse> customerResponse = customerService.getCustomerPerPage(pageable, customerRequest);

        if (customerResponse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PageResponse<CustomerResponse> pageResponse = new PageResponse<>(customerResponse);

        return ResponseEntity.ok(pageResponse);
    }

    @PostMapping("/avatar")
    public ResponseEntity<CommonResponse<AvatarResponse>> uploadAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String fileName = fileStorageService.storeFile(avatar, userId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APIUrl.CUSTOMER_API)
                .path("/images/")
                .path(fileName)
                .toUriString();

        AvatarResponse avatarResponse = AvatarResponse.builder()
                .url(fileDownloadUri)
                .name(fileName)
                .build();

        CommonResponse<AvatarResponse> commonResponse = CommonResponse.<AvatarResponse>builder()
                .message("File uploaded successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(Optional.of(avatarResponse))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

}
