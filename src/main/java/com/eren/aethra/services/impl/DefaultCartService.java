package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.ModelDao;
import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Entry;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.CartService;
import com.eren.aethra.services.ProductService;
import com.eren.aethra.services.SessionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultCartService implements CartService {

    @Resource
    private SessionService sessionService;

    @Resource
    private ProductService productService;

    @Resource
    private ModelDao modelDao;

    @Override
    public Cart getCartForCustomer() {
        Cart cart = sessionService.getCurrentCart();
        validateCart(cart);
        return cart;
    }

    @Override
    public void validateCart(Cart cart) {
        cart.getEntries().forEach(entry -> {
            if(entry.getProduct().getStockValue() < entry.getQuantity()) {
                entry.setQuantity(entry.getProduct().getStockValue());
                modelDao.save(entry);
            }
        });
    }

    @Override
    public void addProductToCart(String productCode, Integer qty) throws Exception {
        Entry entry = new Entry();
        Product product = productService.findProductByCode(productCode);
        if (qty < product.getStockValue()) {
            if(qty <= 0){
                throw new Exception(Exceptions.QUANTITY_OF_THE_PRODUCT_CANT_BE_NEGATIVE + productCode);
            }
            entry.setProduct(product);
            entry.setQuantity(qty); {

            }
            Cart cart = sessionService.getCurrentCart();
            if(Objects.nonNull(cart)){
                List<Entry> entries = CollectionUtils.isNotEmpty(cart.getEntries()) ? cart.getEntries() : new ArrayList<>();
                entries.add(entry);
                cart.setEntries(entries);
            } else {
                cart = new Cart();
                cart.setEntries(Collections.singletonList(entry));
            }
            modelDao.save(cart);
        } else {
            throw new Exception(Exceptions.NOT_ENOUGH_STOCK);
        }
    }

    @Override
    public void removeProductFromCart(String productCode) throws Exception {
        Product productModel = productService.findProductByCode(productCode);
        Cart cart = sessionService.getCurrentCart();
        if(Objects.nonNull(cart)){
            List<Entry> entries = cart.getEntries();
            Entry targetEntry = entries.stream().filter(entry -> entry.getProduct().equals(productModel)).findFirst()
                    .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_REMOVE + productCode));
            entries.remove(targetEntry);
            cart.setEntries(entries);
            modelDao.save(cart);
        } else {
            throw new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_REMOVE);
        }
    }

    @Override
    public void updateProductQuantity(String productCode, Integer qty) throws Exception {
        Product product = productService.findProductByCode(productCode);
        Cart cart = sessionService.getCurrentCart();
        if (qty < product.getStockValue()) {
            if(qty <= 0){
                throw new Exception(Exceptions.QUANTITY_OF_THE_PRODUCT_CANT_BE_NEGATIVE + productCode);
            }
            if(Objects.nonNull(cart)){
                Entry targetEntry = cart.getEntries().stream().filter(entry -> entry.getProduct().equals(product)).findFirst()
                        .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_INCREASE));
                targetEntry.setQuantity(qty);
                modelDao.save(targetEntry);
            }
        } else {
            throw new Exception(Exceptions.NOT_ENOUGH_STOCK);
        }
    }
}
