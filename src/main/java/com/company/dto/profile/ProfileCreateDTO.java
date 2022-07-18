package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDTO {
    @NotBlank(message = "username required MAZGI")
    @Size(min = 1, max = 255,message = "title must be between 1 and 255 characters")
    private String username;
    @NotBlank(message = "email required MAZGI")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format error MAZGI")
    private String email;
    @NotBlank(message = "password required MAZGI")
    private String password;
//    @EnumNamePattern(regexp = "NEW|DEFAULT")
    private ProfileRole role;
    private String imageId;
}
