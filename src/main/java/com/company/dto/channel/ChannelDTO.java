package com.company.dto.channel;

import com.company.dto.AttachDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.enums.ChanelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor

public class ChannelDTO {
    private String id;
    private String name;
    private String description;
    private ChanelStatus status;
    private LocalDateTime createdDate;
    private Boolean visible;
    private AttachDTO photo;
    private AttachDTO banner;
    private ProfileDTO profile;

    private String photoId;
    private String photoUrl;
    private String bannerId;
    private String bannerUrl;
}
