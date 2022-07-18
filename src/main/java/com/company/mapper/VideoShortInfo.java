package com.company.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfo {
//    VideShortInfo(id,key,title, preview_attach(id,url),
//    published_date, channel(id,name,photo(url)),
//    view_count,duration)
    String getVideoId();
    String getVideoName();
    String getVideoImageID();
    LocalDateTime getVideoPublishedDate();

    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();

    Integer getVideoViewCount();
    Long getVideoDuration();
}
