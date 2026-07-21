package com.erp.shipping.controller;

import com.erp.shipping.dto.AddressVerificationResponse;
import com.erp.shipping.service.AddressVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressVerificationController {

    private final AddressVerificationService addressVerificationService;

    public AddressVerificationController(AddressVerificationService addressVerificationService) {
        this.addressVerificationService = addressVerificationService;
    }

    /**
     * Örnek: GET /api/shipping/verify-address?country=us&postalCode=90210
     */
    @GetMapping("/api/shipping/verify-address")
    public ResponseEntity<AddressVerificationResponse> verifyAddress(
            @RequestParam String country,
            @RequestParam String postalCode) {

        AddressVerificationResponse response =
                addressVerificationService.verifyPostalCode(country, postalCode);

        return ResponseEntity.ok(response);
    }
}