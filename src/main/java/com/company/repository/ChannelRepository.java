package com.company.repository;

import com.company.dto.channel.ChannelDTO;
import com.company.entity.ChannelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity,String> {
    Optional<ChannelEntity> findByName(String name);

    @Query("from ChannelEntity where visible = true and profile.id =?1")
    List<ChannelEntity> findByProfile_Id(Integer id);
}
