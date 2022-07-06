package com.company.entity;

import com.company.enums.CategoryStatus;
import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String namee;
    @Column
    private CategoryStatus status;

    @Column
    private LocalDateTime createdDate;
}
