package com.tb.eatclean.entity.product;

import com.tb.eatclean.common.ConvertArrayType;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.comment.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double price;
    private String description;
    private int quantity;
    @Column(name = "imgs", length = 20480)
    private String imgs;
    private String slug;
    private String shortDescription;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToMany
    private Set<Categorie> categories;

    @ManyToMany
    private List<Cart> carts;

    @OneToMany
    private List<Comment> comments;

    public void setCategory(List<Long> idsCategory){
        this.categories = idsCategory.stream().map(idCategory -> {
            Categorie categories1 = new Categorie();
            categories1.setId(idCategory);
            return categories1;
        }).collect(Collectors.toSet());
    }

    public void setCategory(Set<Categorie> categories) {
        this.categories = categories;
    }

    public List<String> getStringArray() {
        return ConvertArrayType.convertToList(this.imgs);
    }
}
