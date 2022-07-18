package com.company.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)


public class ChangeEmailDTO {
    @NotBlank(message = "username required MAZGI")
    private String username;
    @NotBlank(message = "email required MAZGI")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format error MAZGI")
    private String email;
    @NotBlank(message = "password required MAZGI")
    private String password;

}
