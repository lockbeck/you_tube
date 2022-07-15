package com.company.service;

import com.company.dto.CommentLikeDTO;
import com.company.dto.VideoLikeDTO;
import com.company.entity.*;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentLikeRepository;
import com.company.repository.VideoLikeRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommnetService commnetService;

    public void articleLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(Integer commentId, Integer pId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findExists(commentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean videoExists = commnetService.existsById(commentId);
        if (!videoExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment(new CommentEntity(commentId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        commentLikeRepository.save(like);
    }

    public void removeLike(Integer commentId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        commentLikeRepository.delete(commentId, pId);
    }

//    public Integer getNumberOfLikeByArticleId(String id) {
//        return articleLikeRepository.countByArticleId(id ,LikeStatus.LIKE);
//    }
    public CommentLikeDTO getNumLikes(Integer id) {
        HashMap<String, Integer> map = commentLikeRepository.countLikes(id);
        CommentLikeDTO dto = new CommentLikeDTO();
        dto.setLikes(map.get("likes"));
        dto.setDislikes(map.get("dislikes"));
        return dto;
    }
}
