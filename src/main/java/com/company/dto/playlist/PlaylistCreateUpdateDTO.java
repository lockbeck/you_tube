package com.company.dto.playlist;

import com.company.enums.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor

public class PlaylistCreateUpdateDTO {

    @NotBlank(message = "name required MAZGI")
    private String name;
    @NotBlank(message = "description required MAZGI")
    private String description;
    @NotBlank(message = "orderNumber required MAZGI")
    private Integer orderNumber;
    @NotBlank(message = "status required MAZGI")
    private PlayListStatus status;
    @NotBlank(message = "channelId required MAZGI")
    private String channelId;
    private String attachId;
}
