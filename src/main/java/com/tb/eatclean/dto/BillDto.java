package com.tb.eatclean.dto;

import com.tb.eatclean.entity.bill.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {
  private Long id;
  private String date;
  private BillStatus status;
  private Long userId;

  private Map<String, Object> listFoods;

  private Map<String, Object> userInfo;
}
