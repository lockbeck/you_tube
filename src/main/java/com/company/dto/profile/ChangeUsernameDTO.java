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


public class ChangeUsernameDTO {
    @NotBlank(message = "username required MAZGI")
    private String username;
    @NotBlank(message = "newUsername required MAZGI")
    private String newUsername;

}
