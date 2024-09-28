package com.example.courseapp.specification;

import com.example.courseapp.model.Address;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {
    public static Specification<Address> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("city"), "%" + city + "%");
    }

    public static Specification<Address> hasState(String state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("state"), "%" + state + "%");
    }

    public static Specification<Address> hasCountry(String country) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("country"), "%" + country + "%");
    }
}
