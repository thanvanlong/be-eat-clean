package com.tb.eatclean.repo;

import com.tb.eatclean.entity.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<Blog, Long> {
}
