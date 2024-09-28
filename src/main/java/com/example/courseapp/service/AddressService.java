package com.example.courseapp.service;

import com.example.courseapp.model.Address;
import com.example.courseapp.repository.AddressRepository;
import com.example.courseapp.specification.AddressSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> filterAddresses(String city, String state, String country) {
        Specification<Address> spec = Specification.where(null);

        if (city != null && !city.isEmpty()) {
            spec = spec.and(AddressSpecification.hasCity(city));
        }
        if (state != null && !state.isEmpty()) {
            spec = spec.and(AddressSpecification.hasState(state));
        }
        if (country != null && !country.isEmpty()) {
            spec = spec.and(AddressSpecification.hasCountry(country));
        }

        return addressRepository.findAll(spec);
    }
}
