package com.company.dto;

import com.company.entity.AttachEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    @NotBlank(message = "username required MAZGI")
    @Size(min = 1, max = 255,message = "title must be between 1 and 255 characters")
    private String username;
   // @NotBlank(message = "email required MAZGI")
    @Pattern(regexp = "^(.+)@(.+)$", message = "email format error MAZGI")
    private String email;
    @NotBlank(message = "password required MAZGI")
    private String password;
    private String imageId;

    private ProfileRole role;
    private ProfileStatus status;

    private LocalDateTime createdDate;
    private Boolean visible;

    private AttachDTO image;

    private String jwt;
}
