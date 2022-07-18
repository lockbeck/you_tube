package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.*;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.ProfileForPlaylistInfo;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private EmailService emailService;

    public ProfileDTO create(ProfileCreateDTO dto) {
        // name; surname; email; password;

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        if (dto.getImageId() != null) {
            entity.setImage(attachService.get(dto.getImageId()));
        }

        profileRepository.save(entity);
        dto.setPassword(null);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setUsername(dto.getUsername());
        profileDTO.setEmail(dto.getEmail());
        profileDTO.setRole(dto.getRole());
        return profileDTO;
    }

//    public void update(Integer profileId, ProfileDTO dto) {
//        ProfileEntity entity = get(profileId);
//
//        isValid(dto);
//
//        if (entity.getPhoto() == null && dto.getPhotoId() != null) {
//            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
//        } else if (entity.getPhoto() != null && dto.getPhotoId() == null) {
//            profileRepository.updatePhotoById(profileId, dto.getPhotoId());
//            attachService.delete(entity.getPhoto().getId());
//            entity.setPhoto(null);
//        } else if (entity.getPhoto() != null && dto.getPhotoId() != null &&
//                !entity.getPhoto().getId().equals(dto.getPhotoId())) {
//            profileRepository.updatePhotoById(profileId, dto.getPhotoId());
//            attachService.delete(entity.getPhoto().getId());
//            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
//        }
//
//        entity.setName(dto.getName());
//        entity.setSurname(dto.getSurname());
//        entity.setEmail(dto.getEmail());
//        entity.setPassword(dto.getPassword());
//
//        profileRepository.save(entity);
//    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found");
        });
    }

    public ProfileEntity getByUsername(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found MAZGI");
        });
    }

    public ProfileEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return principal.getProfile();
    }

    public String changePassword(ChangePasswordDTO dto) {
        ProfileEntity currentUser = getByUsername(dto.getUsername());

        if (!currentUser.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            throw new BadRequestException("old password wrong MAZGI");
        }
        if (!dto.getNewPassword().equals(dto.getNewConfirmedPassword())) {
            throw new BadRequestException("confirmation of new password error MAZGI");
        }
        profileRepository.changePassword(currentUser.getId(), MD5Util.getMd5(dto.getNewPassword()));

        return "Password successfully changed";
    }

    public String changeEmail(ChangeEmailDTO dto) {
        ProfileEntity profile = getByUsername(dto.getUsername());
        if (profile.getEmail().equals(dto.getEmail())) {
            throw new BadRequestException("No changes between new and old email");
        }
        if (profileRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("this email already exists");
        }
        if (!profile.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new BadRequestException("Password is wrong MAZGI");
        }
        profileRepository.updateEmail(profile.getId(), dto.getEmail(), ProfileStatus.NOT_ACTIVE);

        emailService.sendRegistrationEmail(dto.getEmail(), profile.getId());

        return "Activation code was sent to " + dto.getEmail();
    }

    public String changeUsername(ChangeUsernameDTO dto) {
        ProfileEntity currentUser = getByUsername(dto.getUsername());
        currentUser.setUsername(dto.getNewUsername());
        profileRepository.save(currentUser);
        return "Username updated successfully";
    }

    public String changeAttach(ChangeAttachDTO dto) {
        ProfileEntity currentUser = getByUsername(dto.getUsername());
        if (currentUser.getImage() == null) {
            currentUser.setImage(attachService.get(dto.getAttachId()));

        } else {
            String image = currentUser.getImage().getId();
            currentUser.setImage(attachService.get(dto.getAttachId()));
            profileRepository.save(currentUser);
            attachService.delete(image);
        }
        profileRepository.save(currentUser);

        return "Image updated successfully";
    }

    public  List<ProfileDTO> getInfo() {
        Iterable<ProfileEntity> all = profileRepository.findAll();
        List<ProfileDTO> dtoList= new LinkedList<>();
        all.forEach(profileEntity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setUsername(profileEntity.getUsername());
            dto.setEmail(profileEntity.getEmail());
            dto.setId(profileEntity.getId());
            if (profileEntity.getImage() != null) {
                dto.setImageUrl(attachService.getImageUrl(profileEntity.getImage().getId()));
            }
            dtoList.add(dto);
        });
        return dtoList;
    }

//    public List<ProfileDTO> getProfileInfo(Integer profileId) {
//        List<ProfileForPlaylistInfo> profileInfo = profileRepository.getProfileInfo(profileId);
//        List<ProfileDTO> dtoList = new LinkedList<>();
//        profileInfo.forEach(profile -> {
//            ProfileDTO profileDTO = new ProfileDTO();
//            profileDTO.setId(profile.getProfileId());
//            profileDTO.setUsername(profile.getProfileUsername());
//            profileDTO.setImageId(profile.getPhotoId());
//            profileDTO.setImageUrl(attachService.getImageUrl(profile.getProfileUsername()));
//            dtoList.add(profileDTO);
//        });
//        return dtoList;
//    }
}
