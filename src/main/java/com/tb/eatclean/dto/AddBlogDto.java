package com.tb.eatclean.dto;

import com.tb.eatclean.entity.blog.BlogState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBlogDto {
    private String title;
    private String slug;
    private String content;
    private BlogState blogState = BlogState.Active;
    private String thumbnail;
}
