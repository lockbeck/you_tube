package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistCreateUpdateDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoCreateDTO;
import com.company.dto.video.VideoDTO;
import com.company.dto.video.VideoUpdateDTO;
import com.company.entity.*;
import com.company.enums.PlayListStatus;
import com.company.enums.ProfileRole;
import com.company.enums.VideoStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NoPermissionException;
import com.company.mapper.PlaylistInfo;
import com.company.mapper.PlaylistShortInfo;
import com.company.mapper.VideoShortInfo;
import com.company.repository.PlaylistRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoRepository videoRepository;

    public String create(VideoCreateDTO dto) {
        VideoEntity entity = new VideoEntity();

        if (dto.getImageId() != null) {
            entity.setImage(attachService.get(dto.getImageId()));
        }
        entity.setAttach(attachService.get(dto.getAttachId()));

        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setChannel(channelService.get(dto.getChannelId()));
        entity.setCategory(new CategoryEntity( dto.getCategoryId()));
        videoRepository.save(entity);
        return "Video created";
    }


    public String update(VideoUpdateDTO dto, String channelId) {
        VideoEntity entity = get(channelId);

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        if (entity.getImage() == null && dto.getImageId() != null) {
            entity.setImage(new AttachEntity(dto.getImageId()));
        } else if (entity.getImage() != null && dto.getImageId() == null) {
            videoRepository.updatePhotoById(entity.getId(),dto.getImageId());
            attachService.delete(entity.getImage().getId());
            entity.setImage(null);
        } else if (entity.getImage() != null && dto.getImageId() != null &&
                !entity.getImage().getId().equals(dto.getImageId())) {
            videoRepository.updatePhotoById(entity.getId(),dto.getImageId());
            attachService.delete(entity.getImage().getId());
            entity.setImage(new AttachEntity(dto.getImageId()));
        }
        entity.setCategory(new CategoryEntity(dto.getCategoryId()));



        videoRepository.save(entity);
        return "Video updated";
    }
    public String changeStatus(String id, VideoStatus status) {
        VideoEntity entity = get(id);
        entity.setStatus(status);
        videoRepository.save(entity);
        return "Video status successfully changed";
    }

    public String delete(String id) {
        VideoEntity entity = get(id);
        if (!entity.getChannel().getProfile().equals(profileService.getCurrentUser())||
                !profileService.getCurrentUser().getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new NoPermissionException("No permission MAZGI");
        }
        entity.setVisible(Boolean.FALSE);
        videoRepository.save(entity);
        return "playlist successfully deleted";
    }

    public VideoEntity get(String id) {
        return videoRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Video not found");
        });
    }



    public PageImpl pagination(int page, int size) {

//        VideShortInfo(id,key,title, preview_attach(id,url),
//                published_date, channel(id,name,photo(url)),
//                view_count,duration)
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VideoEntity> all = videoRepository.findAll(pageable);
        List<VideoDTO> dtoList = new LinkedList<>();
        all.forEach(entity -> {
            VideoDTO dto = new VideoDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            if (entity.getImage() != null) {
                dto.setImage(attachService.getDto(entity.getImage().getId()));
            }

            dtoList.add(dto);
        });
        return new PageImpl(dtoList, pageable, all.getTotalElements());

    }

//    public List<PlaylistDTO> getProfilePlaylist(Integer profileID) {
//        List<PlaylistShortInfo> listInfo = videoRepository.getProfilePlayLists(profileID);
//
//
//        List<PlaylistDTO> dtoList = new LinkedList<>();
//        listInfo.forEach(playlistInfo -> {
//            PlaylistDTO dto = new PlaylistDTO();
//            dto.setId(playlistInfo.getPlaylistId());
//            dto.setName(playlistInfo.getPlaylistName());
//            dto.setCreatedDate(playlistInfo.getPlaylistCreatedDate());
//
//            ChannelDTO channelDTO = new ChannelDTO();
//            channelDTO.setId(playlistInfo.getChannelId());
//            channelDTO.setName(playlistInfo.getChannelName());
//            dto.setChanel(channelDTO);
//
//            dto.setNumOfVideos(playlistInfo.getCountVideo());
//            dto.setTotalView(playlistInfo.getTotalWatchedCount());
//
//            dtoList.add(dto);
//
//        });
//        return dtoList;
//    }

//    public List<PlaylistDTO> getPlaylistByProfileId(Integer profileID) {
//        List<PlaylistInfo> listInfo = videoRepository.getPlaylistByProfileId(profileID);
//
//        List<PlaylistDTO> dtoList = new LinkedList<>();
//        listInfo.forEach(playlistInfo -> {
//            PlaylistDTO dto = new PlaylistDTO();
//            dto.setId(playlistInfo.getPlaylistId());
//            dto.setName(playlistInfo.getPlaylistName());
//            dto.setDescription(playlistInfo.getPlaylistDescription());
//            dto.setOrderNumber(playlistInfo.getPlaylistOrderNum());
//
//            ChannelDTO channelDTO = new ChannelDTO();
//            channelDTO.setId(playlistInfo.getChannelId());
//            channelDTO.setName(playlistInfo.getChannelName());
//            channelDTO.setPhoto(new AttachDTO(playlistInfo.getChannelId(),attachService.getImageUrl(playlistInfo.getChannelPhotoId())));
//
//            ProfileDTO profileDTO = new ProfileDTO();
//            profileDTO.setId(playlistInfo.getProfileId());
//            profileDTO.setUsername(playlistInfo.getProfileUsername());
//            profileDTO.setImage(new AttachDTO(playlistInfo.getProfilePhotoId(), attachService.getImageUrl(playlistInfo.getProfilePhotoId())));
//            channelDTO.setProfile(profileDTO);
//            dto.setChanel(channelDTO);
//
//            dtoList.add(dto);
//
//        });
//        return dtoList;
//    }

    public List<VideoDTO> searchByName(String videoName) {
        List<VideoShortInfo> infoList = videoRepository.searchByVideoName(videoName);
        List<VideoDTO> dtoList = new LinkedList<>();
        infoList.forEach(info -> {
            VideoDTO dto = new VideoDTO();
            dto.setId(info.getVideoId());
            dto.setName(info.getVideoName());
            dto.setImage(attachService.getAttachDTOIdUrl(info.getVideoImageID()));
            dto.setPublishedDate(info.getVideoPublishedDate());
            dto.setViewCount(info.getVideoViewCount());
            dto.setDuration(info.getVideoDuration());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(info.getChannelId());
            channelDTO.setName(info.getChannelName());
            channelDTO.setPhoto(new AttachDTO(attachService.getImageUrl(info.getChannelPhotoId())));

            dto.setChannel(channelDTO);

            dtoList.add(dto);

        });
        return dtoList;
    }
}
