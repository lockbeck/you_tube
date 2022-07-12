package com.company.entity;

import com.company.enums.ChanelStatus;
import com.company.enums.PlayListStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playlist")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private PlayListStatus status;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column
    private Boolean visible = Boolean.TRUE;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private AttachEntity photo;

    @JoinColumn(name = "channel_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChannelEntity chanel;





}
