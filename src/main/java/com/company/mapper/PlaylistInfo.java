package com.company.mapper;

import java.time.LocalDateTime;

public interface PlaylistInfo {
    String getPlaylistId();
    String getPlaylistName();
    String getPlaylistDescription();
    String getPlaylistStatus();
    Integer getPlaylistOrderNum();

    String getChannelId();
    String getChannelName();
    String getChannelPhotoId();

    Integer getProfileId();
    String getProfileUsername();
    String getProfilePhotoId();


}
