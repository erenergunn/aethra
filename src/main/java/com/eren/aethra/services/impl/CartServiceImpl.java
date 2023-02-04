package com.eren.aethra.services.impl;

import com.eren.aethra.utils.JwtTokenUtil;
import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.CartDao;
import com.eren.aethra.daos.EntryDao;
import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Entry;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.CartService;
import com.eren.aethra.services.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private ProductService productService;

    @Resource
    private CartDao cartDao;

    @Resource
    private EntryDao entryDao;

    @Override
    public Cart getCartForCustomer() throws Exception {
        Customer customer = jwtTokenUtil.getUserFromToken();
        return customer.getCart();
    }

    @Override
    public void addProductToCart(String productCode, Integer qty) throws Exception {
        Entry entry = new Entry();
        entry.setProduct(productService.findProductByCode(productCode));
        if(qty <= 0){
            throw new Exception(Exceptions.QUANTITY_OF_THE_PRODUCT_CANT_BE_NEGATIVE + productCode);
        }
        entry.setQuantity(qty);
        Customer customer = jwtTokenUtil.getUserFromToken();
        Cart cart;
        if(Objects.nonNull(customer.getCart())){
            cart = customer.getCart();
            List<Entry> entries = CollectionUtils.isNotEmpty(cart.getEntries()) ? cart.getEntries() : new ArrayList<>();
            entries.add(entry);
            cart.setEntries(entries);
        } else {
            cart = new Cart();
            cart.setEntries(Collections.singletonList(entry));
        }
        cartDao.save(cart);
    }

    @Override
    public void removeProductFromCart(String productCode) throws Exception {
        Product productModel = productService.findProductByCode(productCode);
        Customer customer = jwtTokenUtil.getUserFromToken();
        if(Objects.nonNull(customer.getCart())){
            Cart cart = customer.getCart();
            List<Entry> entries = cart.getEntries();
            Entry targetEntry = entries.stream().filter(entry -> entry.getProduct().equals(productModel)).findFirst()
                    .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_REMOVE + productCode));
            entries.remove(targetEntry);
            cart.setEntries(entries);
            cartDao.save(cart);
        } else {
            throw new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_REMOVE);
        }
    }

    @Override
    public void increaseProductQuantity(String productCode) throws Exception {
        Product productModel = productService.findProductByCode(productCode);
        Customer customer = jwtTokenUtil.getUserFromToken();
        if(Objects.nonNull(customer.getCart())){
            Cart cart = customer.getCart();
            List<Entry> entries = cart.getEntries();
            Entry targetEntry = entries.stream().filter(entry -> entry.getProduct().equals(productModel)).findFirst()
                    .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_INCREASE));
            targetEntry.setQuantity(Objects.nonNull(targetEntry.getQuantity()) ? targetEntry.getQuantity() + 1 : 1);
            entryDao.save(targetEntry);
        }
    }

    @Override
    public void decreaseProductQuantity(String productCode) throws Exception {
        Product productModel = productService.findProductByCode(productCode);
        Customer customer = jwtTokenUtil.getUserFromToken();
        if(Objects.nonNull(customer.getCart())){
            Cart cart = customer.getCart();
            List<Entry> entries = cart.getEntries();
            Entry targetEntry = entries.stream().filter(entry -> entry.getProduct().equals(productModel)).findFirst()
                    .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_INCREASE + productCode));
            if(checkQuantityToDecrease(targetEntry.getQuantity())){
                targetEntry.setQuantity(targetEntry.getQuantity() - 1);
            }
            entryDao.save(targetEntry);
        }
    }

    private boolean checkQuantityToDecrease(Integer quantity) throws Exception {
        if(Objects.isNull(quantity)){
            throw new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_DECREASE);
        }
        if(quantity <= 1){
            throw new Exception(Exceptions.QUANTITY_OF_THE_PRODUCT_CANT_BE_REDUCED);
        }
        return true;
    }

}
