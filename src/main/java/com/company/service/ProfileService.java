package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;

    public ProfileDTO create(ProfileDTO dto) {
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
        dto.setId(entity.getId());
        return dto;
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
}
