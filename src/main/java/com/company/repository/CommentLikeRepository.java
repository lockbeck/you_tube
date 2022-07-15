package com.company.repository;

import com.company.entity.CommentLikeEntity;
import com.company.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {
    @Query("FROM VideoLikeEntity v where  v.video.id=:commentId and v.profile.id =:pId")
    Optional<CommentLikeEntity> findExists(Integer commentId, Integer pId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CommentLikeEntity a where  a.comment.id=:commentId and a.profile.id =:profileId")
    void delete(Integer commentId ,Integer profileId);

    @Query(value = "SELECT cast(SUM (CASE WHEN t.status = 'LIKE'  THEN 1 ELSE 0 END)as int) AS likes," +
            "cast(SUM (CASE WHEN t.status = 'DISLIKE'   THEN 1 ELSE 0 END)as int) AS dislikes " +
            "FROM comment_like t where comment_id = ?1 " ,
            nativeQuery = true)
    HashMap<String , Integer> countLikes(Integer id);
}
