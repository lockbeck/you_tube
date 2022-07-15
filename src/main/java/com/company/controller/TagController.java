package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.TagDTO;
import com.company.service.CategoryService;
import com.company.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Tag Controller")
@RestController
@RequestMapping("/tag")
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;
    @ApiOperation(value = "Create", notes = "Method to create tag(only ADMIN)")
    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto) {
        log.info("Request to create tag{}", dto);
        String response = tagService.create(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update tag", notes = "Method to update tag(only ADMIN)")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid TagDTO dto) {
        log.info("Request to update tag{}", dto);
        String response = tagService.update(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete tag", notes = "Method to delete tag(only ADMIN)")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        log.info("Request to delete tag{}", id);
        String response = tagService.delete(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all tags", notes = "Method to get all tags(only ADMIN)")
    @GetMapping("/public/getAll")
    public ResponseEntity<?> getAll() {
        log.info("Request to get all tags");
        List<TagDTO> response = tagService.getAll();
        return ResponseEntity.ok(response);
    }
}
