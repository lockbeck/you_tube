package com.company.dto.subscription;

import com.company.dto.channel.ChannelDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.VideoEntity;
import com.company.enums.Notification;
import com.company.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class SubscriptionDTO {

    private Integer id;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    private Integer profileId;
    private String channelId;

    private ProfileDTO profile;
    private ChannelDTO channel;

    private SubscriptionStatus status;
    private Notification notification;
}
