package com.eren.aethra.services;

import com.eren.aethra.dtos.requests.StoreRequest;
import com.eren.aethra.dtos.responses.StoreResponse;
import com.eren.aethra.models.Store;

public interface StoreService {

    StoreResponse getStore();

    Store getStoreModel();

    void updateStore(StoreRequest storeRequest);

}
