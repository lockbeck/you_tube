package com.company.dto.profile;

import com.company.dto.AttachDTO;
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
    private String username;
    private String email;
    private String password;
    private String imageId;

    private ProfileRole role;
    private ProfileStatus status;

    private LocalDateTime createdDate;
    private Boolean visible;

    private AttachDTO image;

    private String jwt;
    private String imageUrl;
}
