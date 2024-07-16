package com.enigmacamp.tokonyadia.service.impl;

import com.enigmacamp.tokonyadia.model.dto.request.MidtransDetailsRequest;
import com.enigmacamp.tokonyadia.model.dto.request.MidtransRequest;
import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.enigmacamp.tokonyadia.model.dto.response.MidtransResponse;
import com.enigmacamp.tokonyadia.model.dto.response.ProductResponse;
import com.enigmacamp.tokonyadia.model.dto.response.TransactionResponse;
import com.enigmacamp.tokonyadia.model.entity.Customer;
import com.enigmacamp.tokonyadia.model.entity.Product;
import com.enigmacamp.tokonyadia.model.entity.Transaction;
import com.enigmacamp.tokonyadia.model.entity.TransactionDetail;
import com.enigmacamp.tokonyadia.repository.TransactionDetailRepository;
import com.enigmacamp.tokonyadia.repository.TransactionRepository;
import com.enigmacamp.tokonyadia.service.CustomerService;
import com.enigmacamp.tokonyadia.service.ProductService;
import com.enigmacamp.tokonyadia.service.TransactionService;

import com.enigmacamp.tokonyadia.utils.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {
        Customer customer = customerService.getById(transactionRequest.getCustomerId());
        Date currentDate = new Date(); // Date on service
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .date(currentDate)
                .build();

        AtomicReference<Long> totalPayment = new AtomicReference<>(0L);

        List<TransactionDetail> transactionDetails = transactionRequest.getTransactionDetails().stream().map(detailRequest -> {
            Product product = productService.getProductById(detailRequest.getProductId());
            if(product.getStock() - detailRequest.getQty() < 0){
                throw new ValidationException("the product currently out of stock");
            }

            product.setStock(product.getStock() - detailRequest.getQty());

            TransactionDetail trxDetail = TransactionDetail.builder()
                    .product(product)
                    .transaction(transaction)
                    .qty(detailRequest.getQty())
                    .productPrice(product.getPrice())
                    .build();

            totalPayment.updateAndGet(v -> v + product.getPrice() * detailRequest.getQty());

            //TODO: Insert Transaction Detail
            transactionDetailRepository.save(trxDetail);
            return trxDetail;
        }).toList();

        //TODO: Insert Transaction
        transaction.setTransactionDetails(transactionDetails);
        Transaction resultTransaction = transactionRepository.saveAndFlush(transaction);


        // Create Midtrans Transaction
        MidtransResponse midtransResponse = getTransaction(resultTransaction.getId(), totalPayment.get());

        //TODO: Create Transaction Response
        return TransactionResponse.builder()
                .id(resultTransaction.getId())
                .customer(resultTransaction.getCustomer())
                .date(resultTransaction.getDate())
                .transactionDetails(resultTransaction.getTransactionDetails())
                .totalPayment(totalPayment.get())
                .midtransResponse(midtransResponse)
                .build();
    }


    public MidtransResponse getTransaction(String transactionId, Long totalPayment) {

        RestTemplate restTemplate = new RestTemplate();

        String snapToken = "SB-Mid-server-vAHvXRrlKiWUWzhmhzk5vyol";
        String baseSnapToken = Base64.getEncoder().encodeToString(snapToken.getBytes(StandardCharsets.US_ASCII));

        // Header
        HttpHeaders headers = new HttpHeaders() {{
            set("Authorization", "Basic " + baseSnapToken);
            set("Content-Type", "application/json");
            set("Accept", "application/json");
        }};

        // Body
        // transaction details
        MidtransDetailsRequest transactionDetails = MidtransDetailsRequest.builder()
                .order_id(transactionId)
                .gross_amount(totalPayment)
                .build();

        // transaction request
        MidtransRequest requestBody = MidtransRequest.builder()
                .transaction_details(transactionDetails).build();

        HttpEntity<MidtransRequest> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                "https://app.sandbox.midtrans.com/snap/v1/transactions",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error While Creating Transaction");
        }

        return MidtransResponse.builder()
                .token(Objects.requireNonNull(response.getBody()).getToken())
                .redirect_url(response.getBody().getRedirect_url())
                .build();
    }


}
