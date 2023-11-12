package com.tb.eatclean.entity.product;

import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.comment.Comment;
import com.tb.eatclean.utils.StringUtils;
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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long price;
    @Column(columnDefinition="text", length=10485760)
    private String description;
    private int quantity;
    @Column(name = "imgs")
    @ElementCollection
    private List<String> imgs;
    private String slug;
    private String shortDescription;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    @ManyToMany
    private Set<Categorie> categories;
    @OneToMany
    private List<Comment> comments;
    private String searchName;

    @Transient
    private boolean canComment;

    @Transient
    private int orderCount;

//    public void setCategory(List<Long> idsCategory){
//        this.categories = idsCategory.stream().map(idCategory -> {
//            Categorie categories1 = new Categorie();
//            categories1.setId(idCategory);
//            return categories1;
//        }).collect(Collectors.toSet());
//    }

    public void setName(String name) {
        this.name = name;
        this.searchName = StringUtils.removeAccents(this.name).toLowerCase();
    }

//    public void setCategories(Set<Categorie> categories) {
//        this.categories = categories;
//    }

    public void setCategories(Set<Long> categoryIds) {
        this.categories = categoryIds.stream().map(id -> {
            Categorie categorie = new Categorie();
            categorie.setId(id);
            return categorie;
        }).collect(Collectors.toSet());
    }
}
