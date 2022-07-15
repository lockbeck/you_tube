package com.company.dto.report;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ReportDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private Integer profileId;
    private ProfileDTO profile;
    private String entityId;
    private String content;
    private ReportType type;
}
