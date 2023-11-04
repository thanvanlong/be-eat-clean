package com.tb.eatclean.entity.blog;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String title;
    @Column(columnDefinition="text")
    private String content;
    private String imgThumbnail;
}
