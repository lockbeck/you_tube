package com.company.controller;

import com.company.dto.channel.ChannelCreateUpdateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.service.ChannelService;
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
@RequestMapping("/channel")
@Slf4j
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @ApiOperation(value = "Create", notes = "Method to create channel")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChannelCreateUpdateDTO dto) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Update channel", notes = "Method to update channel")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ChannelCreateUpdateDTO dto,
                                        @PathVariable ("id")  String channelId) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.update(dto,channelId);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change photo", notes = "Method to change channel photo")
    @PutMapping("/public/changePhoto/{id}")
    public ResponseEntity<?> changePhoto(@RequestBody @Valid ChannelDTO dto,
                                        @PathVariable ("id") String channelId) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.changePhoto(dto.getPhotoId(),channelId);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change banner", notes = "Method to change channel banner")
    @PutMapping("/public/changeBanner/{id}")
    public ResponseEntity<?> changeBanner(@RequestBody ChannelDTO dto,
                                         @PathVariable("id") String channelId) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.changeBanner(dto.getPhotoId(),channelId);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Channel pagination", notes = "Method to get channel pagination(only ADMIN)")
    @GetMapping("/adm/pagination/{id}")
    public ResponseEntity<?> pagination(@RequestParam(value = "page",defaultValue = "0") int page,
                                        @RequestParam(value = "size",defaultValue = "0") int size) {
        log.info("Request to channel pagination ");
        PageImpl response = channelService.pagination(page,size);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Get channel by id", notes = "Method to get channel")
    @GetMapping("/public/get/{id}")
    public ResponseEntity<?> pagination(@PathVariable("id") String id) {
        log.info("Request to get channel");
        ChannelDTO response = channelService.getById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change channel status", notes = "Method to change channel status")
    @GetMapping("/public/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id) {
        log.info("Request to get channel");
        String response = channelService.changeStatus(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "User channel list", notes = "Method to get user channel list")
    @GetMapping("/public/getUserChanne")
    public ResponseEntity<?> changeStatus() {
        log.info("Request to get channel");
        List<ChannelDTO> response = channelService.getUserChannelList();
        return ResponseEntity.ok(response);
    }


}
