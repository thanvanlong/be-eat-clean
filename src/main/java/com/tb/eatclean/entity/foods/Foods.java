package com.tb.eatclean.entity.foods;

import com.tb.eatclean.entity.CommonObjectDTO;
import com.tb.eatclean.entity.carts.Carts;
import com.tb.eatclean.entity.categories.Categories;
import com.tb.eatclean.entity.comments.Comments;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Foods extends CommonObjectDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private double price;
    private String description;
    private int quantity;

    @ManyToMany
    @Transient
    private Set<Categories> categories;

    @Transient
    @OneToMany
    private List<Carts> carts;

    @OneToMany
    @Transient
    private List<Comments> comments;

    public void setCategory(List<Long> idsCategory){
        this.categories = idsCategory.stream().map(idCategory -> {
            Categories categories1 = new Categories();
            categories1.setId(idCategory);
            return categories1;
        }).collect(Collectors.toSet());
    }
}
