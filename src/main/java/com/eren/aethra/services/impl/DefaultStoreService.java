package com.eren.aethra.services.impl;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.daos.StoreDao;
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
}
