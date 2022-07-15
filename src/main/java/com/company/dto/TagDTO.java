package com.company.dto;

import com.company.enums.CategoryStatus;
import com.company.enums.TagStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class TagDTO {
    private Integer id;
    @NotBlank(message = "name is empty or null MAZGI")
    @Pattern(regexp = "^#(.+)$", message = "tag name format error MAZGI " +
            " (Example: #example)")
    private String name;
    private TagStatus status;
    private LocalDateTime createdDate;
}
