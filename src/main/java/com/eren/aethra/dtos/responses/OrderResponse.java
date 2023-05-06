package com.eren.aethra.dtos.responses;

import com.eren.aethra.enums.OrderStatus;
import com.eren.aethra.models.Entry;
import lombok.Data;

import java.util.Set;

@Data
public class OrderResponse {

    String pk;

    Set<Entry> entries;

    Double totalPriceOfProducts;

    Double shippingPrice;

    Double totalPrice;

    OrderStatus orderStatus;

    AddressResponse address;

    String shippingTrackingLink;

}
