package com.company.entity;

import com.company.enums.PlaylistVideoStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_video")
@Getter
@Setter
public class PlayListVideoEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String id;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false, insertable = false,updatable = false)
    private VideoEntity video;

    @Column(name = "playlist_id")
    private String playlistId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false, insertable = false, updatable = false)
    private PlayListEntity playlist;


    @Column(name = "order_number")
    private Integer order;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaylistVideoStatus status = PlaylistVideoStatus.ACTIVE;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

}
