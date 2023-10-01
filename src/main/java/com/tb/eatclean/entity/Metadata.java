package com.tb.eatclean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private int pageNumber;
    private int pageSize;
    private int totalPage;
}
