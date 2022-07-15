package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeDTO {
    private Integer commentId;
    private Integer likes;
    private Integer dislikes;

}
