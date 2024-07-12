package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.SellerRequest;
import com.enigmacamp.tokonyadia.model.dto.response.CustomerResponse;
import com.enigmacamp.tokonyadia.model.dto.response.SellerResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Seller;
import com.enigmacamp.tokonyadia.repository.SellerRepository;
import com.enigmacamp.tokonyadia.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Override
    public SellerResponse createSeller(SellerRequest request) {
        Seller seller = new Seller();
        seller.setName(request.getName());
        seller.setPhoneNumber(request.getPhoneNumber());
        seller.setAddress(request.getAddress());
        seller.setBirthDate(request.getBirthDate());
        seller.setUser(request.getUser());

        // Save customer
        seller = sellerRepository.saveAndFlush(seller);
        return convertToSellerResponse(seller);
    }

    private SellerResponse convertToSellerResponse(Seller seller) {
        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setId(seller.getId());
        sellerResponse.setName(seller.getName());
        sellerResponse.setPhoneNumber(seller.getPhoneNumber());
        sellerResponse.setAddress(seller.getAddress());
        sellerResponse.setBirthDate(seller.getBirthDate());

        return sellerResponse;
    }
}
