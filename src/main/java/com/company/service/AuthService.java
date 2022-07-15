package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.LoginDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.entity.EmailHistoryEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.EmailHistoryRepository;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> byUsername = profileRepository.findByUsername(dto.getUsername());
        if(byUsername.isPresent()){
            throw  new BadRequestException("username already registered");
        }
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            throw  new BadRequestException("email already registered");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));

        if(dto.getImageId()!=null){
            entity.setImage(attachService.get(dto.getImageId()));
        }

        entity.setRole(ProfileRole.ROLE_PROFILE);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        emailService.sendRegistrationEmail(entity.getEmail(), entity.getId());

        return "Activation code was sent to " + dto.getEmail();

//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setId(entity.getId());
//        profileDTO.setUsername(entity.getUsername());
//        profileDTO.setEmail(entity.getEmail());
//        profileDTO.setRole(entity.getRole());
//        profileDTO.setCreatedDate(entity.getCreatedDate());
//        return  profileDTO;



    }

    public ProfileDTO login(LoginDTO dto) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        ProfileEntity profile = user.getProfile();
        if (profile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
            throw new BadRequestException("Profile is not ACTIVE");
        }

       /* Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        ProfileEntity profile = optional.get();
        if (!MD5Util.getMd5(authDTO.getPassword()).equals(profile.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }*/

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(profile.getUsername());
        profileDTO.setEmail(profile.getEmail());

        profileDTO.setJwt(JwtUtil.encode(profile.getId()));

        return profileDTO;
    }

    public String emailVerification(Integer id) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()) {
            log.warn("User not found by token");
            return "<h1>User Not Found</h1>";
        }

        ProfileEntity profileEntity = profile.get();
        Optional<EmailHistoryEntity> emailHistory = emailHistoryRepository.findTopByEmailOrderByCreatedDateDesc(profileEntity.getEmail());
        if (emailHistory.isEmpty()) {
            return "Email Not Found";
        }

        EmailHistoryEntity email = emailHistory.get();
        LocalDateTime validDate = email.getCreatedDate().plusMinutes(1);

        if (validDate.isBefore(LocalDateTime.now())) {
            return "<h1 style='align-text:center'>Time is out.</h1>";
        }

        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profileEntity);
        return "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>";
    }

    public ResponseInfoDTO resendEmail(String email) {


        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }

        ProfileEntity entity = byEmail.get();
        if (entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("User has already been verified by email");
        }
        Long count = emailService.getCountByEmail(email);
        if (count >= 3) {
            throw new BadRequestException("Too many attempts. Try later");
        }
        emailService.sendRegistrationEmail(email, entity.getId());
        return new ResponseInfoDTO(1, "Link was sent to " + email);
    }
}
