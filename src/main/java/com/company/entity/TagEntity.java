package com.company.entity;

import com.company.enums.TagStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tag")
@NoArgsConstructor
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TagStatus status = TagStatus.ACTIVE;

    @Column
    private LocalDateTime createdDate = LocalDateTime.now();

    public TagEntity(String name) {
        this.name = name;
    }
}
