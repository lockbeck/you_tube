package com.company.dto.video;

import com.company.dto.AttachDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.entity.AttachEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.enums.VideoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class VideoDTO {
    private String id;
    private String name;
    private String description;
    private VideoStatus status;
    private Integer viewCount;
    private Integer sharedCount;
    private Long duration;

    private AttachDTO image;
    private AttachDTO attach;
    private ChannelDTO channel;
    private CategoryDTO category;

    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
}
