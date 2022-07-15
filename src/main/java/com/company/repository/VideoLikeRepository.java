package com.company.repository;

import com.company.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity,Integer> {
    @Query("FROM VideoLikeEntity v where  v.video.id=:videoId and v.profile.id =:pId")
    Optional<VideoLikeEntity> findExists(String videoId, Integer pId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity a where  a.video.id=:videoId and a.profile.id =:profileId")
    void delete(String videoId ,Integer profileId);

    @Query(value = "SELECT cast(SUM (CASE WHEN t.status = 'LIKE'  THEN 1 ELSE 0 END)as int) AS likes," +
            "cast(SUM (CASE WHEN t.status = 'DISLIKE'   THEN 1 ELSE 0 END)as int) AS dislikes " +
            "FROM video_like t where video_id = ?1 " ,
            nativeQuery = true)
    HashMap<String , Integer> countLikes(String id);
}
