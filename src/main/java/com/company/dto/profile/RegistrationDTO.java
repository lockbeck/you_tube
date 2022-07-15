package com.company.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {

    @NotBlank(message = "username required MAZGI")
    @Size(min = 1, max = 255,message = "title must be between 1 and 255 characters")
    private String username;
    @NotBlank(message = "email required MAZGI")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format error MAZGI")
    private String email;
    @NotBlank(message = "password required MAZGI")
    private String password;
    private String imageId;
}
