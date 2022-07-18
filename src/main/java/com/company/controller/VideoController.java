package com.company.controller;

import com.company.dto.playlist.PlaylistCreateUpdateDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.video.VideoCreateDTO;
import com.company.dto.video.VideoDTO;
import com.company.dto.video.VideoUpdateDTO;
import com.company.enums.VideoStatus;
import com.company.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Video Controller")
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;
    @ApiOperation(value = "Create video", notes = "Method to create video")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody @Valid VideoCreateDTO dto) {
        log.info("Request for registration{}", dto);
        String profileDTO = videoService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Update video", notes = "Method to update video")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid VideoUpdateDTO dto,
                                        @PathVariable ("id")  String playlistID) {
        log.info("Request for registration{}", dto);
        String profileDTO = videoService.update(dto,playlistID);
        return ResponseEntity.ok(profileDTO);
    }



    @ApiOperation(value = "video pagination", notes = "Method to get video pagination(only ADMIN)")
    @GetMapping("/adm/pagination/{id}")
    public ResponseEntity<?> pagination(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "size",defaultValue = "0") int size) {
        log.info("Request to channel pagination ");
        PageImpl response = videoService.pagination(page,size);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Change playlist status", notes = "Method to change playlist status")
    @GetMapping("/public/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestBody VideoCreateDTO dto) {
        log.info("Request to get channel");
        String response = videoService.changeStatus(id,dto.getStatus());
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete video", notes = "Method to delete video")
    @GetMapping("/public/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        log.info("Request to delete video");
        String response = videoService.delete(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "serach video", notes = "Method to search video")
    @GetMapping("/public/search/{videoName}")
    public ResponseEntity<?> searchByName(@PathVariable ("videoName") String videoName) {
        log.info("Request to get channel");
        List<VideoDTO> response = videoService.searchByName(videoName);
        return ResponseEntity.ok(response);
    }
//
//    @ApiOperation(value = "Get playlist by userid for user", notes = "Method to get playlist by user id(USER)")
//    @GetMapping("/adm/getProfilePlaylist/{profileId}")
//    public ResponseEntity<?> getProfilePlaylist(@PathVariable ("profileId") Integer profileID) {
//        log.info("Request to get channel");
//        List<PlaylistDTO> response = videoService.getProfilePlaylist(profileID);
//        return ResponseEntity.ok(response);
//    }



}
