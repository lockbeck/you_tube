package com.company.service;

import com.company.dto.VideoLikeDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.entity.VideoLikeEntity;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private VideoRepository videoRepository;

    public void articleLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(String videoId, Integer pId, LikeStatus status) {
        Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, pId);
        if (optional.isPresent()) {
            VideoLikeEntity like = optional.get();
            like.setStatus(status);
            videoLikeRepository.save(like);
            return;
        }
        boolean videoExists = videoRepository.existsById(videoId);
        if (!videoExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        VideoLikeEntity like = new VideoLikeEntity();
        like.setVideo(new VideoEntity(videoId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        videoLikeRepository.save(like);
    }

    public void removeLike(String articleId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        videoLikeRepository.delete(articleId, pId);
    }

//    public Integer getNumberOfLikeByArticleId(String id) {
//        return articleLikeRepository.countByArticleId(id ,LikeStatus.LIKE);
//    }
    public VideoLikeDTO getNumLikes(String id) {
        HashMap<String, Integer> map = videoLikeRepository.countLikes(id);
        VideoLikeDTO dto = new VideoLikeDTO();
        dto.setLikes(map.get("likes"));
        dto.setDislikes(map.get("dislikes"));
        return dto;
    }
}
