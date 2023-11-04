package com.tb.eatclean.service.blog;

import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.repo.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    BlogRepo blogRepo;
    @Override
    public Map<String, Object> pagingSort(int page, int limit) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Blog> foodsPage = blogRepo.findAll(pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(foodsPage.getNumber());
        metadata.setPageSize(foodsPage.getSize());
        metadata.setTotalPages(foodsPage.getTotalPages());
        metadata.setTotalItems(foodsPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", foodsPage.getContent());
        response.put("metadata", metadata);

        return response;
    }

    @Override
    public Blog getById(long id) {
        Optional<Blog> data = blogRepo.findById(id);
        return data.isPresent() ? data.get() : null;
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepo.save(blog);
    }
}
