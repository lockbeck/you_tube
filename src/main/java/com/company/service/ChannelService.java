package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.channel.ChannelCreateUpdateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChanelStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChannelService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @Autowired
    private ChannelRepository channelRepository;

    public String create(ChannelCreateUpdateDTO dto) {
        Optional<ChannelEntity> byId = channelRepository.findByName(dto.getName());
        if (byId.isPresent()) {
            throw new BadRequestException("Channel already exists");
        }

        ChannelEntity entity = new ChannelEntity();
        entity.setProfile(profileService.getCurrentUser());
        if (dto.getPhotoId() != null) {
            entity.setPhoto(attachService.get(dto.getPhotoId()));
        }
        if (dto.getBannerId() != null) {
            entity.setBanner(attachService.get(dto.getBannerId()));
        }

        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setStatus(ChanelStatus.ACTIVE);
        channelRepository.save(entity);
        return "Channel created";
    }


    public String update(ChannelCreateUpdateDTO dto, String channelId) {
        ChannelEntity entity = get(channelId);
        if (entity.getProfile().equals(profileService.getCurrentUser())) {
            throw new BadRequestException("Channel can be updated by its owner only MAZGI");
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        if (dto.getPhotoId() != null) {
            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
        }
        if (dto.getBannerId() != null) {
            entity.setBanner(new AttachEntity(dto.getBannerId()));
        }
        channelRepository.save(entity);
        return "Channel updated";
    }

    public ChannelEntity get(String id) {
        return channelRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Channel not found");
        });
    }

    public String changePhoto(String photoId, String channelId) {
        ChannelEntity entity = get(channelId);
        if (photoId == null) {
            throw new BadRequestException("photo id null or empty MAZGI");
        }
        if (entity.getProfile().equals(profileService.getCurrentUser())) {
            throw new BadRequestException("Channel can be updated by its owner only MAZGI");
        }

        if (entity.getPhoto() == null) {
            entity.setPhoto(attachService.get(photoId));
            channelRepository.save(entity);
        } else {
            String oldId = entity.getPhoto().getId();
            entity.setPhoto(attachService.get(photoId));
            channelRepository.save(entity);
            attachService.delete(oldId);
        }

        return "Photo set";

    }

    public String changeBanner(String bannerId, String channelId) {
        ChannelEntity entity = get(channelId);
        if (bannerId == null) {
            throw new BadRequestException("Banner id null or empty MAZGI");
        }

        if (entity.getBanner() == null) {
            entity.setBanner(attachService.get(bannerId));
            channelRepository.save(entity);
        } else {
            String oldId = entity.getPhoto().getId();
            entity.setBanner(attachService.get(bannerId));
            channelRepository.save(entity);
            attachService.delete(oldId);
        }

        return "Banner set";

    }

    public PageImpl pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ChannelEntity> all = channelRepository.findAll(pageable);
        List<ChannelDTO> dtoList = new LinkedList<>();
        all.forEach(entity -> {
            ChannelDTO dto = new ChannelDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setCreatedDate(entity.getCreatedDate());
            if (entity.getPhoto() != null) {
                dto.setPhotoUrl(attachService.getImageUrl(entity.getPhoto().getId()));
            }

            dtoList.add(dto);
        });
        return new PageImpl(dtoList, pageable, all.getTotalElements());

    }


    public ChannelDTO getById(String id) {
        ChannelEntity entity = get(id);
        ChannelDTO dto = new ChannelDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setStatus(entity.getStatus());
        dto.setVisible(entity.getVisible());
        if (entity.getPhoto() != null) {
            dto.setPhotoUrl(entity.getPhoto().getId());
        }
        if (entity.getBanner() != null) {
            dto.setBannerUrl(entity.getBanner().getId());
        }
        return dto;
    }

    public String changeStatus(String id) {
        ChannelEntity entity = get(id);
        entity.setStatus(ChanelStatus.NOT_ACTIVE);
        channelRepository.save(entity);
        return "Channel successfully blocked";
    }

    public List<ChannelDTO> getUserChannelList() {
        Integer id = profileService.getCurrentUser().getId();

        List<ChannelDTO> dtoList = new LinkedList<>();
        List<ChannelEntity> byProfile_id = channelRepository.findByProfile_Id(id);
        byProfile_id.forEach(entity -> {

                    ChannelDTO dto = new ChannelDTO();
                    dto.setName(entity.getName());
                    dto.setDescription(entity.getDescription());
                    dto.setCreatedDate(entity.getCreatedDate());
                    if (entity.getPhoto() != null) {
                        dto.setPhoto(attachService.getDto(entity.getId()));
                    }
                    if (entity.getBanner() != null) {
                        dto.setBanner(attachService.getDto(entity.getId()));
                    }
                    dto.setName(entity.getName());
                    dtoList.add(dto);
                }
        );
        return dtoList;
    }
}
