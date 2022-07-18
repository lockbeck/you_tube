package com.company.dto.video;

import com.company.dto.AttachDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.enums.VideoStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class VideoCreateDTO {
    @NotBlank(message = "name required MAZGI")
    private String name;
    @NotBlank(message = "description required MAZGI")
    private String description;
    @NotBlank(message = "status required MAZGI")
    private VideoStatus status;

    private String imageId;
    @NotBlank(message = "attachId required MAZGI")
    private String attachId;
    @NotBlank(message = "channelId required MAZGI")
    private String channelId;
    @NotBlank(message = "categoryId required MAZGI")
    private Integer categoryId;
}
