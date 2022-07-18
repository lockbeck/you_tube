package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.mapper.ProfileForPlaylistInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByUsername(String username);

    Optional<ProfileEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(" update ProfileEntity set password =?2 where id =?1 ")
    void changePassword(Integer id,String newPassword);
    @Modifying
    @Transactional
    @Query(" update ProfileEntity set email =:newEmail , status = :status where id =:profileId")
    void updateEmail(@Param("profileId") Integer id, @Param("newEmail") String email,@Param("status") ProfileStatus status);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(" update ProfileEntity set image.id =?2 where id =?1 ")
    void updateImage(Integer id, String attachId);

    @Query(value = "select p.id as profileId, p.username as profileUsername, p.photo_id as photoId " +
            "from profile as p",nativeQuery = true)
    List<ProfileForPlaylistInfo> getProfileInfo(Integer profileId);
}
