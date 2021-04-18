package com.codegym.airbnb.repositories;

import com.codegym.airbnb.model.PropertyType;
import org.springframework.data.repository.CrudRepository;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, Long> {
}
