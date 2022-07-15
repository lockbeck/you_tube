package com.company.controller;

import com.company.dto.ChannelDTO;
import com.company.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Channel Controller")
@RestController
@RequestMapping("/channel")
@Slf4j
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @ApiOperation(value = "Create", notes = "Method to create channel(only ADMIN)")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChannelDTO dto) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change password", notes = "Method to change channel(only ADMIN)")
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ChannelDTO dto,
                                        @PathVariable String channelId) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.update(dto,channelId);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change password", notes = "Method to change channel photo(only ADMIN)")
    @PostMapping("/changePhoto/{id}")
    public ResponseEntity<?> changePhoto(@RequestBody @Valid ChannelDTO dto,
                                        @PathVariable String channelId) {
        log.info("Request for registration{}", dto);
        String profileDTO = channelService.changePhoto(dto.getPhotoId(),channelId);
        return ResponseEntity.ok(profileDTO);
    }
}
