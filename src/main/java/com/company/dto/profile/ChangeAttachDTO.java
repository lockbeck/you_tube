package com.company.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)


public class ChangeAttachDTO {
    @NotBlank(message = "username required MAZGI")
    private String username;
    @NotBlank(message = "attachId required MAZGI")
    private String attachId;

}
