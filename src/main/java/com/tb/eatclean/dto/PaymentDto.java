package com.tb.eatclean.dto;

import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.user.User;
import lombok.Data;

@Data
public class PaymentDto {
  private String partnerCode;
  private String orderId;
  private String requestId;
  private double amount;
  private String orderInfo;
  private String orderType;
  private String transId;
  private int resultCode;
  private String message;
  private String payType;
  private long responseTime;
  private String extraDate;
  private String signature;
}
