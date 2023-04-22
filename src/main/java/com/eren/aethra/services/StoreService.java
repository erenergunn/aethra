package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.StoreRequest;
import com.eren.aethra.dtos.responses.StoreResponse;

public interface StoreService {

    StoreResponse getStore();

    void updateStore(StoreRequest storeRequest);

}
