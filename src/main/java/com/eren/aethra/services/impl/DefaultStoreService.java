package com.eren.aethra.services.impl;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.daos.StoreDao;
import com.eren.aethra.dtos.requests.StoreRequest;
import com.eren.aethra.dtos.responses.StoreResponse;
import com.eren.aethra.models.Store;
import com.eren.aethra.services.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultStoreService implements StoreService {

    @Resource
    private StoreDao storeDao;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public StoreResponse getStore() {
        Store store = storeDao.findStoreByCode(AethraCoreConstants.AETHRA_STORE_CODE);
        return modelMapper.map(store, StoreResponse.class);
    }

    @Override
    public Store getStoreModel() {
        Store store = storeDao.findStoreByCode(AethraCoreConstants.AETHRA_STORE_CODE);
        if (store == null) {
            store = new Store();
            store.setCode(AethraCoreConstants.AETHRA_STORE_CODE);
            store.setName(AethraCoreConstants.AETHRA_STORE_NAME);
            store.setLogoUrl("/logo");
            store.setShippingCost(10D);
            store.setFreeShippingThreshold(100D);
            storeDao.save(store);
            return storeDao.findStoreByCode(AethraCoreConstants.AETHRA_STORE_CODE);
        }
        return store;
    }

    @Override
    public void updateStore(StoreRequest storeRequest) {
        Store store = modelMapper.map(storeRequest, Store.class);
        storeDao.save(store);
    }
}
