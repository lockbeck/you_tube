package com.company.repository;

import com.company.entity.SubscriptionEntity;
import com.company.enums.SubscriptionStatus;
import com.company.mapper.SubscriptionInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity,Integer> {
    @Query("from SubscriptionEntity where channel.id = ?1 and profile.id =?2")
    Optional<SubscriptionEntity> findByProfile_IdAndChanelId(String channelId, Integer id);

    @Query(value = " select s.id as subscriptionId , c.id as channelId, c.name as channelName," +
            " c.photo_id as channelPhotoId " +
            " from subscription as s " +
            " inner join channel as c on s.channel_id = c.id" +
            "where s.status = ?1 and c.profile_id =?2",nativeQuery = true)
    List<SubscriptionInfo> findByProfileId(SubscriptionStatus status , Integer id);

    @Query(value = " select s.id as subscriptionId , c.id as channelId, c.name as channelName," +
            " c.photo_id as channelPhotoId, s.created_date as createdDate  " +
            " from subscription as s " +
            " inner join channel as c on s.channel_id = c.id" +
            "where s.status = ?1 and c.profile_id =?2",nativeQuery = true)
    List<SubscriptionInfo> findByProfileIdForAdmin(SubscriptionStatus status , Integer id);
}
