package com.hemanth.multithreading.controller;

import com.hemanth.multithreading.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * <p>Description of the class.</p>
 *
 * <p>Author: Hemanth Nagireddy</p>
 * <p>Date: 4/10/2025</p>
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/user/{id}/details")
    public ResponseEntity<String> getUserDetails(@PathVariable String id) throws Exception {

        CompletableFuture<String> profileFuture = customerService.getCustomerProfile(id);
        CompletableFuture<String> historyFuture = customerService.getTransactionHistory(id);

        // Wait for both to complete
        CompletableFuture.allOf(profileFuture, historyFuture).join();

        // Combine results
        String combined = profileFuture.get() + "\n" + historyFuture.get();

        return ResponseEntity.ok(combined);
    }
}

