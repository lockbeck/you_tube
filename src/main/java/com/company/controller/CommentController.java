package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.entity.ProfileEntity;
import com.company.service.CommnetService;
import com.company.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommnetService commnetService;
    @Autowired
    private ProfileService profileService;


    //public

    //secure

//1. CREATE (ANY)
//        (content,article_id,reply_id)
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid CommentDTO dto) {
        log.info("Request for create comment {}",dto);
        ProfileEntity profile = profileService.getCurrentUser();
        commnetService.create(dto,profile);
        return ResponseEntity.ok(dto.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String>show(@PathVariable("id") Integer id){
        log.info("Request for get comment by id {}",id);
        String  dto = commnetService.get(id);
        return ResponseEntity.ok(dto);

    }
    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>>show(){
        log.info("Request for get all comment");
        List<CommentDTO>  dto = commnetService.getAll();
        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String>update(@RequestBody CommentDTO dto,
                                        @PathVariable("id") Integer commentId){
        log.info("Request for update comment {} by id {}",dto, commentId);
        Integer profileId = profileService.getCurrentUser().getId();

        commnetService.update(commentId,dto.getContent(),profileId);
        return ResponseEntity.ok("Successful");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>delete(@PathVariable("id") Integer id){
        log.info("Request for delete comment by id {}",id);
        Integer profileId = profileService.getCurrentUser().getId();

        commnetService.delete(id,profileId);
        return ResponseEntity.ok("Successful");

    }




}
