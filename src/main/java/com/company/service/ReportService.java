package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.report.ReportDTO;
import com.company.entity.ReportEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AttachService attachService ;

    @Autowired
    private ProfileService profileService;
    public String create(ReportDTO dto) {
        ReportEntity entity = new ReportEntity();
        entity.setContent(dto.getContent());
        entity.setType(dto.getType());
        entity.setEntityId(dto.getEntityId());
        entity.setProfile(profileService.getCurrentUser());
        reportRepository.save(entity);
        return "Report created";
    }

    public String delete(Integer id) {

        Optional<ReportEntity> byId = reportRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Report not found");
        }
        reportRepository.delete(byId.get());
        return "Report removed";
    }

    public PageImpl pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportEntity> all = reportRepository.findAll(pageable);

        List<ReportEntity> list = all.getContent();

        List<ReportDTO> dtoList = new LinkedList<>();

        list.forEach(report -> {
                ReportDTO dto = new ReportDTO();
                dto.setContent(report.getContent());
                dto.setCreatedDate(report.getCreatedDate());
                dto.setEntityId(report.getEntityId());
                dto.setType(report.getType());
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setUsername(report.getProfile().getUsername());
            profileDTO.setEmail(report.getProfile().getEmail());
            if(report.getProfile().getImage()!=null) {
                AttachDTO attachDTO= new AttachDTO();
                attachDTO.setId(report.getProfile().getImage().getId());
                attachDTO.setUrl(attachService.getImageUrl(report.getProfile().getImage().getId()));
                profileDTO.setImage(attachDTO);
            }
            profileDTO.setUsername(report.getProfile().getUsername());
                dtoList.add(dto);

        });

        return new PageImpl(dtoList,pageable, all.getTotalElements());
    }

    public List<ReportDTO> getReportsByProfileId(Integer profileId) {
        List<ReportEntity> all = reportRepository.findAllByProfileId(profileId);

        List<ReportDTO> dtoList = new LinkedList<>();

        all.forEach(report -> {
            ReportDTO dto = new ReportDTO();
            dto.setContent(report.getContent());
            dto.setCreatedDate(report.getCreatedDate());
            dto.setEntityId(report.getEntityId());
            dto.setType(report.getType());
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setUsername(report.getProfile().getUsername());
            profileDTO.setEmail(report.getProfile().getEmail());
            if(report.getProfile().getImage()!=null) {
                AttachDTO attachDTO= new AttachDTO();
                attachDTO.setId(report.getProfile().getImage().getId());
                attachDTO.setUrl(attachService.getImageUrl(report.getProfile().getImage().getId()));
                profileDTO.setImage(attachDTO);
            }
            profileDTO.setUsername(report.getProfile().getUsername());
            dtoList.add(dto);

        });
        return dtoList;
    }
}
