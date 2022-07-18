package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.mapper.VideoShortInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends PagingAndSortingRepository<VideoEntity,String> {
    @Query("update VideoEntity set image.id = ?2 where id= ?1")
    void updatePhotoById(String id, String attachId);

    @Query(value = "select v.id as videoId, v.name as videoName, v.image_id as videoImageId " +
            " v.published_date as videoPublishedDate, v.view_count as videoViewCount, v.duration as videoDuration " +
            " c.id as channelId , c.name as channelVideo, c.photo_id as channelPhotoId " +
            " from video as v" +
            " inner join channel as c on v.channel_id = c.id" +
            " where v.vidsible = true and v.name like '% :videoName %'",nativeQuery = true)
    List<VideoShortInfo> searchByVideoName(@Param("videoName") String videoName);
}
