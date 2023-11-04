package com.tb.eatclean.entity.wishlist;

import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Product product;
}
