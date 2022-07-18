package com.company.dto;

import com.company.dto.playlist.PlaylistDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private String path;
    private String url;

    private LocalDateTime createdDate;

    public AttachDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public AttachDTO(String url) {
        this.url = url;
    }
}
