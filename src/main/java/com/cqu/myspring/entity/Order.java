package com.cqu.myspring.entity;

import com.cqu.myspring.Component;
import com.cqu.myspring.Value;
import lombok.Data;

@Data
@Component("myOrder")
public class Order {
    @Value("Djj")
    private String orderId;
    @Value("520")
    private Float price;
}

