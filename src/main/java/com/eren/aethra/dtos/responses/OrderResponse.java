package com.eren.aethra.dtos.responses;

import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.models.Entry;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderResponse {

    String pk;

    Set<EntryResponse> entries;

    Double totalPriceOfProducts;

    Double totalPrice;

    String orderStatus;

    String shippingTrackingLink;

    String createdAt;

}
