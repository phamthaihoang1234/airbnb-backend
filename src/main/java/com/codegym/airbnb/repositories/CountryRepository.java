package com.codegym.airbnb.repositories;

import com.codegym.airbnb.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {
}
