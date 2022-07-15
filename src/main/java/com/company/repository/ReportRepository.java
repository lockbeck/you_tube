package com.company.repository;

import com.company.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReportRepository extends PagingAndSortingRepository<ReportEntity,Integer> {
    List<ReportEntity> findAllByProfileId(Integer profileId);
}
