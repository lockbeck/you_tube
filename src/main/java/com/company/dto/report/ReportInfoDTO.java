package com.company.dto.report;

import com.company.entity.ProfileEntity;
import com.company.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ReportInfoDTO {
    private Integer id;
    private ProfileEntity profile; //(id,name,surname, photo(id,key,url)),
    private String content;
    private Integer entity_id; //(channel/video)),
    private ReportType type; // type(channel,video)
}
