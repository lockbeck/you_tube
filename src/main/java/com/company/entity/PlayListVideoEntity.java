package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_video")
@Getter
@Setter
public class PlayListVideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "playlist_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlayListEntity playList;

    @JoinColumn(name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity video;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
