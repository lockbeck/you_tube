package com.company.controller;

import com.company.dto.channel.ChannelCreateUpdateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistCreateUpdateDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.service.ChannelService;
import com.company.service.PlaylistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Channel Controller")
@RestController
@RequestMapping("/playlist")
@Slf4j
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;
    @ApiOperation(value = "Create", notes = "Method to create channel")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody @Valid PlaylistCreateUpdateDTO dto) {
        log.info("Request for registration{}", dto);
        String profileDTO = playlistService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Update channel", notes = "Method to update channel")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PlaylistCreateUpdateDTO dto,
                                        @PathVariable ("id")  String playlistID) {
        log.info("Request for registration{}", dto);
        String profileDTO = playlistService.update(dto,playlistID);
        return ResponseEntity.ok(profileDTO);
    }



    @ApiOperation(value = "playlist pagination", notes = "Method to get playlist pagination(only ADMIN)")
    @GetMapping("/adm/pagination/{id}")
    public ResponseEntity<?> pagination(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "size",defaultValue = "0") int size) {
        log.info("Request to channel pagination ");
        PageImpl response = playlistService.pagination(page,size);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Change playlist status", notes = "Method to change playlist status")
    @GetMapping("/public/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestBody PlaylistDTO dto) {
        log.info("Request to get channel");
        String response = playlistService.changeStatus(id,dto.getStatus());
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete playlist", notes = "Method to delete playlist")
    @GetMapping("/public/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        log.info("Request to get channel");
        String response = playlistService.delete(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get playlist by userid", notes = "Method to get playlist by user id(ADMIN)")
    @GetMapping("/adm/getPlaylistByProfileId/{profileId}")
    public ResponseEntity<?> getPlaylistByProfileId(@PathVariable ("profileId") Integer profileID) {
        log.info("Request to get channel");
        List<PlaylistDTO> response = playlistService.getPlaylistByProfileId(profileID);
        return ResponseEntity.ok(response);
    }
//
    @ApiOperation(value = "Get playlist by userid for user", notes = "Method to get playlist by user id(USER)")
    @GetMapping("/adm/getProfilePlaylist/{profileId}")
    public ResponseEntity<?> getProfilePlaylist(@PathVariable ("profileId") Integer profileID) {
        log.info("Request to get channel");
        List<PlaylistDTO> response = playlistService.getProfilePlaylist(profileID);
        return ResponseEntity.ok(response);
    }



}
