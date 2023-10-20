package com.tb.eatclean.entity.bills;

import com.tb.eatclean.entity.CommonObjectDTO;
import com.tb.eatclean.entity.carts.Carts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bills extends CommonObjectDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String date;
  private BillStatus status;
  private Long userId;

  @Column(name = "list_books",columnDefinition = "json")
  private String listBooks;

  @Column(name = "user_info",columnDefinition = "json")
  private String userInfo;

  @Transient
  @OneToMany
  private List<Carts> listProducts;

  @Transient
  private Map<String, Object> info;
}
