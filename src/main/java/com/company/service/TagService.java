package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.TagDTO;
import com.company.entity.CategoryEntity;
import com.company.entity.TagEntity;
import com.company.enums.CategoryStatus;
import com.company.enums.TagStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public String create(TagDTO dto) {
        Optional<TagEntity> byName = tagRepository.findByName(dto.getName());
        if(byName.isPresent()){
            throw new BadRequestException("Tag already exists");
        }

        TagEntity entity = new TagEntity(dto.getName());
        tagRepository.save(entity);
       return "Tag created";
    }

    public String update( TagDTO dto) {
        Optional<TagEntity> byName = tagRepository.findById(dto.getId());
        if(byName.isEmpty()){
            throw new ItemNotFoundException("Tag not found");
        }
        TagEntity entity = byName.get();
        if(entity.getStatus().equals(TagStatus.NOT_ACTIVE)){
            throw new BadRequestException("Tag is not active MAZGI");
        }
        if(entity.getName().equals(dto.getName())){
            throw new BadRequestException("No changes MAZGI");
        }
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return "Successfully updated";

    }

    public String delete(Integer id) {
        Optional<TagEntity> byName = tagRepository.findById(id);
        if(byName.isEmpty()){
            throw new ItemNotFoundException("Tag not found");
        }
        TagEntity entity = byName.get();
        entity.setStatus(TagStatus.NOT_ACTIVE);
        tagRepository.save(entity);
        return "Successfully deleted";
    }

    public List<TagDTO> getAll(){
        Iterable<TagEntity> all = tagRepository.findAll();
        List<TagDTO> list = new LinkedList<>();
        all.forEach(entity -> {
            TagDTO dto = new TagDTO();
            dto.setName(entity.getName());
            dto.setId(entity.getId());
            dto.setStatus(entity.getStatus());
            dto.setCreatedDate(entity.getCreatedDate());
            list.add(dto);
        });
        return list;
    }
}
