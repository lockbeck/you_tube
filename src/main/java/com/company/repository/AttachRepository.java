package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends PagingAndSortingRepository<AttachEntity, String> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM attach WHERE id = ?1", nativeQuery = true)
    void deleteById(String postId);
}
