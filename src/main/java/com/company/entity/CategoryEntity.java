package com.company.entity;

import com.company.enums.CategoryStatus;
import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor

public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CategoryStatus status = CategoryStatus.ACTIVE;
    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity(Integer id) {
        this.id = id;
    }
}
