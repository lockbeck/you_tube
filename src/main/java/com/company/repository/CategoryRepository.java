package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.enums.CategoryStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    @Query("from CategoryEntity where name = ?1 and status = 'ACTIVE'")
    Optional<CategoryEntity> findByName(String name);
}
