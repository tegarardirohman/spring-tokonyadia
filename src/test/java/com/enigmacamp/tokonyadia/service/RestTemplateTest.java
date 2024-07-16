package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.dto.request.MidtransDetailsRequest;
import com.enigmacamp.tokonyadia.model.dto.request.MidtransRequest;
import com.enigmacamp.tokonyadia.model.dto.request.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Data
@Builder
class Todo {
    private Integer userId, id;
    private String title;
    private Boolean completed;
}

@Data
@Builder
class Post{
    private Integer userId, id;
    private String title;
    private String body;
}

public class RestTemplateTest {

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
       restTemplate = new RestTemplate();
    }

    @Test
    void getMapTest() {
        ResponseEntity<Post> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1", Post.class);
        Post body = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assert body != null;
        System.out.println(body.getTitle());

    }

    @Test
    void getClassTest() {
        ResponseEntity<List<Todo>> response = restTemplate.exchange(
                "https://jsonplaceholder.typicode.com/todos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        for (Todo todo : Objects.requireNonNull(response.getBody())) {
            System.out.println(todo.toString());
        }

    }

    @Test
    void postTest() {
        Post post = Post.builder()
                .userId(1)
                .id(1)
                .title("Ini title guys")
                .body("ini body ya")
                .build();

        ResponseEntity<Post> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", post, Post.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        System.out.println(response.getBody());
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodeAuth);
            set("Authorization", authHeader);
            set("Content-Type", "application/json");
        }};
    }

    @Test
    void getWithAuthTest() {
        String username = "admin";
        String password = "1234";
        String token = "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";

        HttpHeaders headers = new HttpHeaders() {{
            set("Authorization", "Bearer " + token);
        }};

        // Basic Auth
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    void midTrans() {
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
                .order_id("first_order_id")
                .gross_amount(10000L)
                .build();

        // transaction request
        MidtransRequest requestBody = MidtransRequest.builder()
                .transaction_details(transactionDetails).build();

        System.out.println(requestBody.toString());

        HttpEntity<MidtransRequest> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://app.sandbox.midtrans.com/snap/v1/transactions",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println(response.getBody());

    }

}


