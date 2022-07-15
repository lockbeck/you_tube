package com.company.controller;

import com.company.dto.profile.LoginDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Authorization and Registration")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;




//    1.Registration(with email verification)

    @ApiOperation(value = "Registration", notes = "Method to registration")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        log.info("Request for registration {}", dto);
        String profileDTO = authService.registration(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Login", notes = "Method to login ")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        log.info("Request for login {}", dto);
        ProfileDTO profileDTO = authService.login(dto);
        return ResponseEntity.ok(profileDTO);
    }


    @ApiOperation(value = "Email verification", notes = "Method for verify email")
    @GetMapping("/email/verification/{token}")
    public ResponseEntity<String> emailVrf(@ApiParam(value = "token", required = true, example = "token of user")
                                        @PathVariable("token") String token) {
        log.info("Request for email verification{}",token);
        Integer id = JwtUtil.decode(token);
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Resend email", notes = "Method for email resending")
    @GetMapping("/resend/{email}")
    public ResponseEntity<ResponseInfoDTO> resendEmail(@ApiParam(value = "email", readOnly = true, example = "your_email@gmail.com")
                                                       @PathVariable("email") String email) {

        log.info("Request for resend email {}",email);
        ResponseInfoDTO responseInfoDTO = authService.resendEmail(email);
        return ResponseEntity.ok(responseInfoDTO);
    }

//    2.Authorization
//    id,name,surname,email,main_photo(url)

}
