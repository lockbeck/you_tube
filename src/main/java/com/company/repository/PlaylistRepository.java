package com.company.repository;

import com.company.entity.ChannelEntity;
import com.company.entity.PlayListEntity;
import com.company.entity.ProfileEntity;
import com.company.mapper.CustomPlaylistRepository;
import com.company.mapper.PlaylistInfo;
import com.company.mapper.PlaylistShortInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends PagingAndSortingRepository<PlayListEntity, String> {

//    @Query(value = "select c.* " +
//            "from playlist c " +
//            "order by c.created_date " +
//            "limit :limit " +
//            "offset :offset", nativeQuery = true)
//    List<PlayListEntity> pagination(@Param("limit") Integer limit, @Param("offset") Integer offset);
//

//
//
//

//
//
//    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
//            "      c.uuid as channleId, c.name as channelName, " +
//            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo " +
//            "     from  playlist as p " +
//            " inner JOIN channel as c on p.channel_id = c.uuid " +
//            "     where c.profile_id =1 " +
//            "     and c.visible = true and p.visible = true ", nativeQuery = true)
//    List<PlaylistShortInfo> getChannelPlayLists(@Param("profileId") String profileId);
//
//
//    @Query("select pe.id as playlistId,pe.name as playlistName, " +
//            " pe.updatedDate as playlistUpdatedDate," +
//            " count(pve.id) as videoCount, " +
//            " sum(pve.video.viewCount) as totalViewCount " +
//            " from PlayListEntity pe " +
//            " inner join PlayListVideoEntity pve" +
//            " on pe.id=pve.playlistId " +
//            " where pe.id = :id" +
//            " group by pe.id,pe.name,pe.updatedDate")
//    Optional<CustomPlaylistRepository> getPlaylistById(@Param("id") String id);
//
//
//    //     id,name,video_count,last_update_date,
//
//    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
//            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo " +
//            "     from  playlist as p " +
//            "     Where p.uuid = :playListId " +
//            "     and p.visible = true ", nativeQuery = true)
//    PlaylistShortInfo getPlaylistShortInfo(@Param("playListId") String playListId);
//
//    //     total_view_count (shu play listdagi videolarni ko'rilganlar soni),
//    @Query(value = " Select cast(count(*) as int) as totalCount" +
//            " from profile_watched_video as  pwv " +
//            " inner join playlist_video as pv on pv.video_id = pwv.video_id " +
//            " Where pv.playlist_id =:playlistId ", nativeQuery = true)
//    Integer getTotalWatchedVideoCount(@Param("playListId") String playListId);
//
//
//    @Query(value = "SELECT p.uuid as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
//            "   (select count(*) from playlist_video  as pv where pv.playlist_id = p.uuid ) as countVideo, " +
//            "   (select cast(count(*) as int) " +
//            "       from profile_watched_video as  pwv " +
//            "       inner join playlist_video as pv on pv.video_id = pwv.video_id " +
//            "       where pv.playlist_id =:playlistId ) as totalWatchedCount" +
//            " from  playlist as p " +
//            " Where p.uuid = :playListId " +
//            " and p.visible = true ", nativeQuery = true)
//    PlaylistShortInfo getPlaylistShortInfoWithTotalWatchedCount(@Param("playListId") String playListId);

    @Modifying
    @Transactional
    @Query("update PlayListEntity as p set p.photo.id =?2 where p.id =?1")
    void updatePhotoById(String profileId, String photoId);

    Optional<ChannelEntity> findByName(String name);

    @Query(value = " select p.id as playlistId p.name as playlistName, p.description as playlistDescription, " +
            " p.status as playlistStatus, p.order_number as playlistOrderNum , " +
            " c.name as channelName , c.id as channelId , c.photo-id as channelPhotoId," +
            " pr.id as profileId , pr.username as profileUsername, pr.image_id as profilePhotoId " +
            " from playlist as p " +
            " inner join channel as c on c.id = p.channel_id" +
            " inner join profile as pr on pr.id = c.profile_id",nativeQuery = true)
    List<PlaylistInfo> getPlaylistByProfileId(Integer profileID);

    @Query(value = "SELECT p.id as playlistId, p.name as playListName, p.created_date as playListCreatedDate, " +
            "      c.id as channleId, c.name as channelName, " +
            " (select count(*) from playlist_video  as pv where pv.playlist_id = p.id ) as countVideo " +
            "     from  playlist as p " +
            " inner JOIN channel as c on p.channel_id = c.uuid " +
            "     where c.profile_id =:profileId " +
            "     and c.visible = true and p.visible = true " +
            "order by p.ord", nativeQuery = true)
    List<PlaylistShortInfo> getProfilePlayLists(@Param("profileId") Integer profileId);

}
