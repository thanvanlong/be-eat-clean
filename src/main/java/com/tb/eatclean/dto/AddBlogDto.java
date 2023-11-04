package com.tb.eatclean.dto;

import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.blog.BlogState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBlogDto {
    private String title;
    private String content;
    private String setImgThumbnail;
    private String description;
    private BlogState blogState = BlogState.Active;
}
