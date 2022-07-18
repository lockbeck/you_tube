package com.company.dto.channel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor

public class ChannelCreateUpdateDTO {
    private String id;
    @NotBlank(message = "name required MAZGI")
    private String name;
    @NotBlank(message = "description required MAZGI")
    private String description;

    private String photoId;
    private String bannerId;
}
