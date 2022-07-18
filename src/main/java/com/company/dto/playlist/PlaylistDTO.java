package com.company.dto.playlist;

import com.company.dto.AttachDTO;
import com.company.dto.video.VideoDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.enums.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class PlaylistDTO {

    private String id;
    private String name;
    private String description;
    private Integer orderNumber;
    private String attachId;
    private String channelId;
    private PlayListStatus status;
    private List<VideoDTO> videoList;
    private LocalDateTime createdDate;
    private Boolean visible;
    private AttachDTO photo;
    private ChannelDTO chanel;

    private Integer numOfVideos;
    private Integer totalView;
}
