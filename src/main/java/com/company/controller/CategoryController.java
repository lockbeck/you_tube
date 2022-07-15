package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Category Controller")
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ApiOperation(value = "Create", notes = "Method to create category(only ADMIN)")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto) {
        log.info("Request to create category{}", dto);
        String response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update category", notes = "Method to update category(only ADMIN)")
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<?> update(@PathVariable  Integer id,@RequestBody @Valid CategoryDTO dto) {
        log.info("Request to update category{}", id);
        String response = categoryService.update(id,dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete category", notes = "Method to delete category(only ADMIN)")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        log.info("Request to delete category{}", id);
        String response = categoryService.delete(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all categories", notes = "Method to get all categories(only ADMIN)")
    @GetMapping("/public/getAll")
    public ResponseEntity<?> getAll() {
        log.info("Request to get all categories");
        List<CategoryDTO> response = categoryService.getAll();
        return ResponseEntity.ok(response);
    }
}
