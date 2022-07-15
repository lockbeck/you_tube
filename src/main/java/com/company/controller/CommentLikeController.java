package com.company.controller;

import com.company.dto.CommentLikeDTO;
import com.company.dto.VideoLikeDTO;
import com.company.service.CommentLikeService;
import com.company.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/commnet_like")
@RestController
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;
    @Autowired
    private ProfileService profileService;

//     16. Article LikeCreate (ANY)
//        (article_id)
    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommentLikeDTO dto) {
        log.info("Request to like video{}",dto);
        Integer profileId = profileService.getCurrentUser().getId();
        commentLikeService.articleLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

//    17. Article DisLikeCreate (ANY)
//            (article_id)
    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommentLikeDTO dto) {
        log.info("Request to dislike video {}",dto);
        Integer profileId = profileService.getCurrentUser().getId();
        commentLikeService.articleDisLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

//    18. Article Like Remove (ANY)
//        (article_id)
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommentLikeDTO dto) {
        log.info("Request to remove like {}",dto);
        Integer profileId = profileService.getCurrentUser().getId();
        commentLikeService.removeLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

}
