package com.company.controller;

import com.company.dto.profile.ProfileDTO;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Profile Controller")
@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @ApiOperation(value = "Create", notes = "Method to create profile(only ADMIN)")
    @PostMapping("/create")
    public ResponseEntity<?> registration(@RequestBody @Valid ProfileDTO dto) {
        log.info("Request for registration{}", dto);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change password", notes = "Method to change password(only ADMIN)")
    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestBody @Valid ProfileDTO dto) {
        log.info("Request for registration{}", dto);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }
}
