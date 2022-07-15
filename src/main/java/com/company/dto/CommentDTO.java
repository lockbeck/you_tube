package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    private String content;
    private String videoId;
    private Integer profileId;
    private Integer replyId;
    private LocalDateTime  createdDate;
    private LocalDateTime  updatedDate;
}
