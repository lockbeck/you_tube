package com.company.entity;

import com.company.enums.ChanelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "channel")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ChannelEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private ChanelStatus status =ChanelStatus.ACTIVE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column
    private Boolean visible = Boolean.TRUE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private AttachEntity photo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private AttachEntity banner;

    @JoinColumn(name = "profile_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    public ChannelEntity(String id) {
        this.id = id;
    }
}
