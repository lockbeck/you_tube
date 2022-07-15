package com.company.dto;

import com.company.enums.CategoryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "name is empty or null MAZGI")
    private String name;
    private CategoryStatus status;
    private LocalDateTime createdDate;
}
