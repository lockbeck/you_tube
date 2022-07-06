package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository  extends CrudRepository<ProfileEntity,Integer> {
    Optional<ProfileEntity> findByUsername(String username);

    Optional<ProfileEntity> findByEmail(String email);
}
