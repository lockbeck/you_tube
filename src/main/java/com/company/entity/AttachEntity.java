package com.company.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "attach")
@NoArgsConstructor

public class AttachEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @Column
    private String originalName;
    @Column
    private String extension;
    @Column
    private Long size;
    @Column
    private String path;

    @Column
    private LocalDateTime createdDate=LocalDateTime.now();
}