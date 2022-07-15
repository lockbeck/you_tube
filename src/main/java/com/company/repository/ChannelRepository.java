package com.company.repository;

import com.company.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity,String> {
    Optional<ChannelEntity> findByName(String name);
}
