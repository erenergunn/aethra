package com.eren.aethra.services.impl;

import com.eren.aethra.constants.Exceptions;
import com.eren.aethra.daos.ModelDao;
import com.eren.aethra.helpers.RatingHelper;
import com.eren.aethra.models.Cart;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Entry;
import com.eren.aethra.models.Product;
import com.eren.aethra.services.CartService;
import com.eren.aethra.services.ProductService;
import com.eren.aethra.services.SessionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class DefaultCartService implements CartService {

    @Resource
    private SessionService sessionService;

    @Resource
    private ProductService productService;

    @Resource
    private ModelDao modelDao;

    @Resource
    private RatingHelper ratingHelper;

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
        calculateCart(cart);
    }

    @Override
    public void calculateCart(Cart cart) {
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        cart.getEntries().forEach(entry -> {
            Double price = entry.getProduct().getPrice();
            Integer quantity = entry.getQuantity();
            totalPrice.updateAndGet(v -> v + price * quantity);
        });
        Double dPrice = totalPrice.get();
        BigDecimal bdPrice = BigDecimal.valueOf(dPrice).setScale(2, RoundingMode.HALF_UP);
        cart.setTotalPriceOfProducts(bdPrice.doubleValue());
    }

    @Override
    public void addProductToCart(String productCode, Integer qty) throws Exception {
        Customer customer = sessionService.getCurrentCustomer();
        Cart cart = sessionService.getCurrentCart();
        if (cart != null && cart.getEntries().size() > 0
                && cart.getEntries().stream().anyMatch(entry -> entry.getProduct().getCode().equals(productCode))) {
            throw new Exception("You already have this product on cart");
        }
        Entry entry = new Entry();
        Product product = productService.findProductByCode(productCode);
        if (qty < product.getStockValue()) {
            if(qty <= 0){
                throw new Exception(Exceptions.QUANTITY_OF_THE_PRODUCT_CANT_BE_NEGATIVE + productCode);
            }
            entry.setProduct(product);
            entry.setQuantity(qty);
            modelDao.save(entry);
            if(Objects.nonNull(cart)){
                Set<Entry> entries = CollectionUtils.isNotEmpty(cart.getEntries()) ? cart.getEntries() : new HashSet<>();
                entries.add(entry);
                cart.setEntries(entries);
            } else {
                cart = new Cart();
                cart.setEntries(Collections.singleton(entry));
                cart.setCustomer(customer);
            }
            List<Product> products = cart.getEntries().stream().map(Entry::getProduct).collect(Collectors.toList());
            ratingHelper.createOrRecalculateRatingOfP2P(products, product, 1D, 3);
            ratingHelper.createOrRecalculateRatingOfC2P(cart.getCustomer(), product, 1.5, 5);
            calculateCart(cart);
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
            Set<Entry> entries = cart.getEntries();
            Entry targetEntry = entries.stream().filter(entry -> entry.getProduct().equals(productModel)).findFirst()
                    .orElseThrow(() -> new Exception(Exceptions.THERE_IS_NO_PRODUCT_TO_REMOVE + productCode));
            entries.remove(targetEntry);
            cart.setEntries(entries);
            calculateCart(cart);
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
                calculateCart(cart);
                modelDao.save(targetEntry);
            }
        } else {
            throw new Exception(Exceptions.NOT_ENOUGH_STOCK);
        }
    }
}
