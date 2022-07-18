package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.VideoLikeDTO;
import com.company.dto.channel.ChannelCreateUpdateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistCreateUpdateDTO;
import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.PlayListEntity;
import com.company.enums.ChanelStatus;
import com.company.enums.PlayListStatus;
import com.company.enums.ProfileRole;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NoPermissionException;
import com.company.mapper.PlaylistInfo;
import com.company.mapper.PlaylistShortInfo;
import com.company.repository.ChannelRepository;
import com.company.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private PlaylistRepository playlistRepository;

    public String create(PlaylistCreateUpdateDTO dto) {
        Optional<ChannelEntity> byId = playlistRepository.findByName(dto.getName());
        if (byId.isPresent()) {
            throw new BadRequestException("Playlist already exists");
        }

        PlayListEntity entity = new PlayListEntity();

        if (dto.getAttachId() != null) {
            entity.setPhoto(attachService.get(dto.getAttachId()));
        }

        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setStatus(dto.getStatus());
        entity.setChanel(channelService.get(dto.getChannelId()));
        playlistRepository.save(entity);
        return "Playlist created";
    }


    public String update(PlaylistCreateUpdateDTO dto, String channelId) {
        PlayListEntity entity = get(channelId);

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setStatus(dto.getStatus());
        entity.setChanel(channelService.get(dto.getChannelId()));
        if (entity.getPhoto() == null && dto.getAttachId() != null) {
            entity.setPhoto(new AttachEntity(dto.getAttachId()));
        } else if (entity.getPhoto() != null && dto.getAttachId() == null) {
            playlistRepository.updatePhotoById(entity.getId(),dto.getAttachId());
            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(null);
        } else if (entity.getPhoto() != null && dto.getAttachId() != null &&
                !entity.getPhoto().getId().equals(dto.getAttachId())) {
            playlistRepository.updatePhotoById(entity.getId(),dto.getAttachId());
            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(new AttachEntity(dto.getAttachId()));
        }


        playlistRepository.save(entity);
        return "Playlist updated";
    }
    public String changeStatus(String id,PlayListStatus status) {
        PlayListEntity entity = get(id);
        entity.setStatus(status);
        playlistRepository.save(entity);
        return "Channel status successfully changed";
    }

    public String delete(String id) {
        PlayListEntity entity = get(id);
        if (!entity.getChanel().getProfile().equals(profileService.getCurrentUser())||
                !profileService.getCurrentUser().getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new NoPermissionException("No permission MAZGI");
        }
        entity.setVisible(Boolean.FALSE);
        playlistRepository.save(entity);
        return "playlist successfully deleted";
    }

    public PlayListEntity get(String id) {
        return playlistRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Channel not found");
        });
    }



    public PageImpl pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlayListEntity> all = playlistRepository.findAll(pageable);
        List<PlaylistDTO> dtoList = new LinkedList<>();
        all.forEach(entity -> {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setCreatedDate(entity.getCreatedDate());
            if (entity.getPhoto() != null) {
                dto.setPhoto(attachService.getDto(entity.getPhoto().getId()));
            }

            dtoList.add(dto);
        });
        return new PageImpl(dtoList, pageable, all.getTotalElements());

    }

    public List<PlaylistDTO> getProfilePlaylist(Integer profileID) {
        List<PlaylistShortInfo> listInfo = playlistRepository.getProfilePlayLists(profileID);


        List<PlaylistDTO> dtoList = new LinkedList<>();
        listInfo.forEach(playlistInfo -> {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(playlistInfo.getPlaylistId());
            dto.setName(playlistInfo.getPlaylistName());
            dto.setCreatedDate(playlistInfo.getPlaylistCreatedDate());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(playlistInfo.getChannelId());
            channelDTO.setName(playlistInfo.getChannelName());
            dto.setChanel(channelDTO);

            dto.setNumOfVideos(playlistInfo.getCountVideo());
            dto.setTotalView(playlistInfo.getTotalWatchedCount());

            dtoList.add(dto);

        });
        return dtoList;
    }

    public List<PlaylistDTO> getPlaylistByProfileId(Integer profileID) {
        List<PlaylistInfo> listInfo = playlistRepository.getPlaylistByProfileId(profileID);

        List<PlaylistDTO> dtoList = new LinkedList<>();
        listInfo.forEach(playlistInfo -> {
            PlaylistDTO dto = new PlaylistDTO();
            dto.setId(playlistInfo.getPlaylistId());
            dto.setName(playlistInfo.getPlaylistName());
            dto.setDescription(playlistInfo.getPlaylistDescription());
            dto.setOrderNumber(playlistInfo.getPlaylistOrderNum());

            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(playlistInfo.getChannelId());
            channelDTO.setName(playlistInfo.getChannelName());
            channelDTO.setPhoto(new AttachDTO(playlistInfo.getChannelId(),attachService.getImageUrl(playlistInfo.getChannelPhotoId())));

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(playlistInfo.getProfileId());
            profileDTO.setUsername(playlistInfo.getProfileUsername());
            profileDTO.setImage(new AttachDTO(playlistInfo.getProfilePhotoId(), attachService.getImageUrl(playlistInfo.getProfilePhotoId())));
            channelDTO.setProfile(profileDTO);
            dto.setChanel(channelDTO);

            dtoList.add(dto);

        });
        return dtoList;
    }

}
