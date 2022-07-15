package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity,Integer> {
    @Query("from TagEntity where name = ?1 and status = 'ACTIVE'")
    Optional<TagEntity> findByName(String name);
    Optional<TagEntity> findByStatus(String name);
}
