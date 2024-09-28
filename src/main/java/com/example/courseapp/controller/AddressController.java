package com.example.courseapp.controller;

import com.example.courseapp.model.Address;
import com.example.courseapp.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/filter")
    public ResponseEntity<List<Address>> filterAddresses(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country) {

        List<Address> addresses = addressService.filterAddresses(city, state, country);
        return ResponseEntity.ok(addresses);
    }
}
