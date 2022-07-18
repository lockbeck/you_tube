package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.subscription.SubscriptionCreateDTO;
import com.company.dto.subscription.SubscriptionDTO;
import com.company.dto.subscription.SubscriptionUpdateDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.SubscriptionEntity;
import com.company.enums.Notification;
import com.company.enums.SubscriptionStatus;
import com.company.mapper.SubscriptionInfo;
import com.company.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProfileService profileService;

    public String subscribe(SubscriptionCreateDTO dto) {
        SubscriptionEntity entity = new SubscriptionEntity();

        entity.setChannel(new ChannelEntity(dto.getChannelId()));
        entity.setProfile(profileService.getCurrentUser());

        entity.setStatus(SubscriptionStatus.SUBSCRIBED);
        entity.setNotification(dto.getNotification());
        entity.setCreatedDate(LocalDateTime.now());

        subscriptionRepository.save(entity);

        return "Successfully subscribed";
    }

    public String changeStatus(SubscriptionUpdateDTO dto) {
        Optional<SubscriptionEntity> subscription = subscriptionRepository.
                findByProfile_IdAndChanelId(dto.getChannelId(), profileService.getCurrentUser().getId());
        SubscriptionEntity subscriptionEntity = subscription.get();
        subscriptionEntity.setStatus(dto.getStatus());
        subscriptionEntity.setNotification(Notification.PERSONALIZED);
        subscriptionEntity.setUpdatedDate(LocalDateTime.now());

        subscriptionRepository.save(subscriptionEntity);

        return "Status successfully changed";
    }

    public String changeNotification(SubscriptionCreateDTO dto) {
        Optional<SubscriptionEntity> subscription = subscriptionRepository.
                findByProfile_IdAndChanelId(dto.getChannelId(), profileService.getCurrentUser().getId());
        SubscriptionEntity subscriptionEntity = subscription.get();
        subscriptionEntity.setNotification(dto.getNotification());

        subscriptionRepository.save(subscriptionEntity);

        return "Status successfully changed";
    }

    public List<SubscriptionDTO> getSubsList() {
        List<SubscriptionInfo> infoList = subscriptionRepository.findByProfileId
                (SubscriptionStatus.SUBSCRIBED, profileService.getCurrentUser().getId());

        List<SubscriptionDTO> dtoList= new LinkedList<>();
        infoList.forEach(subscriptionInfo -> {
            SubscriptionDTO dto = new SubscriptionDTO();
            dto.setId(subscriptionInfo.getSubscriptionId());
            dto.setNotification(subscriptionInfo.getNotification());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(subscriptionInfo.getChannelId());
            channelDTO.setName(subscriptionInfo.getChannelName());

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(subscriptionInfo.getChannelPhotoId());
            attachDTO.setUrl(attachService.getImageUrl(subscriptionInfo.getChannelPhotoId()));
            channelDTO.setPhoto(attachDTO);

            dto.setChannel(channelDTO);

            dtoList.add(dto);
        });
        return dtoList;
    }
}
