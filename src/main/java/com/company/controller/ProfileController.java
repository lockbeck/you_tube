package com.company.controller;

import com.company.dto.profile.*;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Profile Controller")
@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @ApiOperation(value = "Create", notes = "Method to create profile(only ADMIN)")
    @PostMapping("/adm/create")
    public ResponseEntity<?> registration(@RequestBody @Valid ProfileCreateDTO dto) {
        log.info("Request to create profile{}", dto);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Change password", notes = "Method to change password")
    @PostMapping("/public/changePass")
    public ResponseEntity<?> changePass(@RequestBody @Valid ChangePasswordDTO dto) {
        log.info("Request to change password {}", dto);
        String response = profileService.changePassword(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change email", notes = "Method to change email")
    @PostMapping("/public/changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody @Valid ChangeEmailDTO dto) {
        log.info("Request to change email{}", dto);
        String response = profileService.changeEmail(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change username", notes = "Method to change username")
    @PostMapping("/public/changeUsername")
    public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameDTO dto) {
        log.info("Request to change username{}", dto);
        String response = profileService.changeUsername(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Change image", notes = "Method to change image")
    @PostMapping("/public/changeImage")
    public ResponseEntity<?> changeImage(@RequestBody ChangeAttachDTO dto) {
        log.info("Request to change image{}", dto);
        String response = profileService.changeAttach(dto);
        return ResponseEntity.ok(response);
    }
    @ApiOperation(value = "Get profile info", notes = "Method to get profile info")
    @GetMapping("/adm/getInfo")
    public ResponseEntity<?> getInfo() {
        log.info("Request to get inf");
        List<ProfileDTO> response = profileService.getInfo();
        return ResponseEntity.ok(response);
    }
}
