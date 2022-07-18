package com.company.mapper;

import com.company.enums.Notification;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

public interface SubscriptionInfo {
//     id,channel(id,name,photo(id,url)),notification_type
    Integer getSubscriptionId();
    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();
    Notification getNotification();
    LocalDateTime getCreatedDate();
}
