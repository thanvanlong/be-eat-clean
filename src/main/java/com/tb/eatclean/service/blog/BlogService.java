package com.tb.eatclean.service.blog;

import com.tb.eatclean.entity.blog.Blog;

import java.util.Map;

public interface BlogService {
    Map<String, Object> pagingSort(int page, int limit);
    Blog getById(long id) throws Exception;

    Blog save(Blog blog);
}
