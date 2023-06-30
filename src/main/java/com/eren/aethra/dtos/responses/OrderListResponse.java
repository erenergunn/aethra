package com.eren.aethra.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class OrderListResponse {

    List<OrderResponse> orders;

}
