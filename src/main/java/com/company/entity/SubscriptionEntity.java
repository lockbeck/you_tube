package com.company.entity;

import com.company.enums.Notification;
import com.company.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
@NoArgsConstructor
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "subscribed_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "unsubscribed_date")
    private LocalDateTime updatedDate;

    @Column(name = "profile_id",insertable = false,updatable = false)
    private Integer profileId;
    @JoinColumn(nullable = false, name = "profile_id")
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @Column(name = "chanel_id", insertable = false,updatable = false)
    private String chanelId;
    @JoinColumn(nullable = false, name = "chanel_id")
    @ManyToOne(targetEntity = VideoEntity.class, fetch = FetchType.LAZY)
    private ChannelEntity channel;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Notification notification;
    public SubscriptionEntity(Integer id) {
        this.id = id;
    }
}
