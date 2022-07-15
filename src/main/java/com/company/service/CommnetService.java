package com.company.service;

import com.company.dto.CommentDTO;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommnetService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;

     public void create(CommentDTO dto, ProfileEntity profileEntity){

         if(profileEntity.getStatus().equals(ProfileStatus.NOT_ACTIVE)){
             throw new BadRequestException("User is blocked");
         }
         if(profileEntity.getVisible().equals(Boolean.FALSE)){
             throw new BadRequestException("User is deleted");
         }

         CommentEntity entity = new CommentEntity();
         entity.setContent(dto.getContent());
         entity.setProfile(profileEntity);
         if(dto.getReplyId()!=null){
             entity.setComment(new CommentEntity(dto.getReplyId()));
         }
         entity.setVideo(new VideoEntity(dto.getVideoId()));
         commentRepository.save(entity);

     }

     public void delete(Integer id, Integer decode){
         Optional<ProfileEntity> profileEntity = profileRepository.findById(decode);
         if(profileEntity.isEmpty()){
             throw new ItemNotFoundException("User not found");
         }
         ProfileEntity entity = profileEntity.get();

         Optional<CommentEntity> byId = commentRepository.findById(id);
         if(byId.isEmpty()){
             throw new ItemNotFoundException("Comment not found");
         }

         CommentEntity comment = byId.get();
         if (!comment.getProfile().equals(entity)) {
             throw new BadRequestException("Comment can be deleted by owner only");
         }
         comment.setVisible(Boolean.FALSE);
         commentRepository.save(comment);
     }

     public void update(Integer id, String content, Integer decode){
         Optional<ProfileEntity> profileEntity = profileRepository.findById(decode);
         if(profileEntity.isEmpty()){
             throw new ItemNotFoundException("User not found");
         }
         ProfileEntity entity = profileEntity.get();
         Optional<CommentEntity> byId = commentRepository.findById(id);
         if(byId.isEmpty()){
             throw new ItemNotFoundException("Comment not found");
         }
         CommentEntity comment = byId.get();
         if (!comment.getProfile().equals(entity)) {
             throw new BadRequestException("Comment can be updated by owner only");
         }
         comment.setContent(content);
         comment.setUpdatedDate(LocalDateTime.now());
         commentRepository.save(comment);
     }
     public String get(Integer id){
         Optional<CommentEntity> byId = commentRepository.findById(id);
         if(byId.isEmpty()){
             throw new ItemNotFoundException("Comment not found");
         }
         return byId.get().getContent();
     }

    public List<CommentDTO> getAll() {
         List<CommentDTO> commentList = new ArrayList<>();
         Iterable<CommentEntity> all = commentRepository.findAll();
        all.forEach(commentEntity ->{
            CommentDTO commentDTO = new CommentDTO();
            if (commentEntity.getVisible().equals(Boolean.TRUE)) {
                commentDTO.setId(commentEntity.getId());
                commentDTO.setContent(commentEntity.getContent());
                commentDTO.setCreatedDate(commentEntity.getCreatedDate());
                if(commentEntity.getUpdatedDate()!=null){
                    commentDTO.setUpdatedDate(commentEntity.getUpdatedDate());
                }
                commentDTO.setProfileId(commentEntity.getProfile().getId());
                commentDTO.setVideoId(commentEntity.getVideo().getId());
                commentList.add(commentDTO);
            }
        });
        return commentList;
    }

    public boolean existsById(Integer commentId) {
        String s = get(commentId);
        return s!=null;

    }
}
