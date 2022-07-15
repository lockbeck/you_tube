package com.company.service;

import com.company.dto.ChannelDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.enums.ChanelStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @Autowired
    private ChannelRepository channelRepository;

    public String create(ChannelDTO dto) {
        Optional<ChannelEntity> byId = channelRepository.findByName(dto.getName());
        if (byId.isPresent()) {
            throw new BadRequestException("Channel already exists");
        }

        ChannelEntity entity = new ChannelEntity();
        entity.setProfile(profileService.getCurrentUser());
        if (dto.getPhotoId() != null) {
            entity.setPhoto(new AttachEntity(dto.getPhotoId()));
        }
        if (dto.getBannerId() != null) {
            entity.setBanner(new AttachEntity(dto.getBannerId()));
        }

        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setStatus(ChanelStatus.ACTIVE);
        channelRepository.save(entity);
        return "Channel created";
    }


    public String update(ChannelDTO dto, String channelId) {
        ChannelEntity entity = get(channelId);
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
        AttachEntity attachEntity = attachService.get(photoId);

        if (entity.getPhoto() == null) {
            entity.setPhoto(attachEntity);
        } else {
            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(attachEntity);
        }
        channelRepository.save(entity);

        return "Photo set";

    }

    public String changeBanner(String bannerId, String channelId) {
        ChannelEntity entity = get(channelId);
        AttachEntity attachEntity = attachService.get(bannerId);

        if (entity.getBanner() == null) {
            entity.setBanner(attachEntity);
        } else {
            attachService.delete(entity.getBanner().getId());
            entity.setBanner(attachEntity);
        }
        channelRepository.save(entity);

        return "Banner set";

    }
}
