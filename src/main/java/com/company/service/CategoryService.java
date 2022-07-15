package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.enums.CategoryStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public String create(CategoryDTO dto) {
        Optional<CategoryEntity> byName = categoryRepository.findByName(dto.getName());
        if(byName.isPresent()){
            throw new BadRequestException("Category already exists");
        }

        CategoryEntity entity = new CategoryEntity(dto.getName());
        entity.setStatus(CategoryStatus.ACTIVE);
       categoryRepository.save(entity);
       return "Category created";
    }

    public String update(Integer id,CategoryDTO dto) {
        Optional<CategoryEntity> byName = categoryRepository.findById(id);
        if(byName.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        CategoryEntity entity = byName.get();
        if(entity.getStatus().equals(CategoryStatus.NOT_ACTIVE)){
            throw new BadRequestException("Category is not active MAZGI");
        }
        if(entity.getName().equals(dto.getName())){
            throw new BadRequestException("No changes MAZGI");
        }
        entity.setName(dto.getName());
        categoryRepository.save(entity);
        return "Successfully updated";

    }

    public String delete(Integer id) {
        Optional<CategoryEntity> byName = categoryRepository.findById(id);
        if(byName.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        CategoryEntity entity = byName.get();
        entity.setStatus(CategoryStatus.NOT_ACTIVE);
        categoryRepository.save(entity);
        return "Successfully deleted";
    }

    public List<CategoryDTO> getAll(){
        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> list = new LinkedList<>();
        all.forEach(entity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setName(entity.getName());
            dto.setId(entity.getId());
            dto.setStatus(entity.getStatus());
            dto.setCreatedDate(entity.getCreatedDate());
            list.add(dto);
        });
        return list;
    }
}
