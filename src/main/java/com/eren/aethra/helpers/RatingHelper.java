package com.eren.aethra.helpers;

import com.eren.aethra.daos.C2PDao;
import com.eren.aethra.daos.P2PDao;
import com.eren.aethra.models.*;
import com.eren.aethra.services.SessionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RatingHelper {

    @Resource
    private P2PDao p2PDao;

    @Resource
    private C2PDao c2PDao;

    @Resource
    private SessionService sessionService;

    public void createOrRecalculateRatingOfC2P(Customer customer, Product product, Double minRating, Integer percentageRate) {
        Customer2ProductRating c2p = c2PDao.findCustomer2ProductRatingsByCustomerAndAndProduct(customer, product);
        if (Objects.isNull(c2p)) {
            c2p = new Customer2ProductRating();
            c2p.setCustomer(customer);
            c2p.setProduct(product);
            c2p.setRating(minRating);
        } else {
            c2p.setRating(calculateNewRating(minRating, percentageRate, c2p.getRating()));
        }
        c2PDao.save(c2p);
    }

    public void createOrRecalculateRatingOfP2P(List<Product> products, Product product, Double minRating, Integer percentageRate) {
        products.forEach(p -> {
            if (!p.getCode().equals(product.getCode())) {
                calculateP2P(product, p, minRating, percentageRate);
                calculateP2P(p, product, minRating, percentageRate);
            }
        });
    }

    public List<Product> getSimilarProducts(Product product) {
        List<Product2ProductRating> p2pRatings = p2PDao.getProduct2ProductRatingsBySource(product);
        Map<Product, Double> map = p2pRatings.stream().limit(8).collect(Collectors.toMap(Product2ProductRating::getTarget, Product2ProductRating::getRating));
        Map<Product, Double> sortedMap = sortByValue(map);
        return new ArrayList<>(sortedMap.keySet());
    }

    public List<Product> getRecommendedProductsForCustomer (Customer customer) {
        Set<Customer2ProductRating> c2pRel = c2PDao.findCustomer2ProductRatingsByCustomer(customer);
        if (CollectionUtils.isEmpty(c2pRel)) {
            return getMostPopularProducts();
        }
        Map<Product, Double> productRatings = new HashMap<>();

        for (Customer2ProductRating c2p : c2pRel) {
            Product product = c2p.getProduct();
            List<Product2ProductRating> p2pList = p2PDao.getProduct2ProductRatingsBySource(product);
            for (Product2ProductRating p2p : p2pList) {
                addC2PToSet(productRatings, p2p.getTarget(), c2p.getRating(), (p2p.getRating()*0.5));
            }
            addC2PToSet(productRatings, c2p.getProduct(), c2p.getRating(), c2p.getRating());
        }
        Cart currentCart = sessionService.getCurrentCart();
        Set<Entry> entries = currentCart.getEntries();
        boolean isSizeOfEntriesGreater = Objects.nonNull(entries) && entries.size() >= 20;
        Map<Product, Double> sortedMap = sortByValue(productRatings);
        int lastIndex = Math.min(isSizeOfEntriesGreater ? entries.size() + 12 : 20, sortedMap.size());
        List<Product> products = new ArrayList<>(sortedMap.keySet())
                .subList(0, lastIndex);

        if (products.size() < 6) {
            List<Product> mostPopularProducts = getMostPopularProducts();
            for (Product product : mostPopularProducts.subList(0, Math.min(12, mostPopularProducts.size()))) {
                if (!products.contains(product)) {
                    products.add(product);
                }
            }
        }

        List<String> productCodesInCart = Collections.emptyList();
        if (!CollectionUtils.isEmpty(entries)) {
            productCodesInCart = entries.stream().map(Entry::getProduct).map(Product::getCode).collect(Collectors.toList());
        }
        List<String> finalProductCodesInCart = productCodesInCart;
        List<Product> productsWithoutInCarts = products.stream().filter(product -> !finalProductCodesInCart.contains(product.getCode())).collect(Collectors.toList());
        return productsWithoutInCarts.subList(0, Math.min(productsWithoutInCarts.size(), 12));
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public List<Product> getMostPopularProducts() {
        List<Product2ProductRating> ratings = p2PDao.getMostPopularProducts();
        return !CollectionUtils.isEmpty(ratings) ? ratings
                .stream()
                .map(Product2ProductRating::getSource)
                .distinct()
                .collect(Collectors.toList()) : null;
    }

    private void addC2PToSet(Map<Product, Double> map, Product product, Double c2pRating, Double p2pRating) {
        if (map.containsKey(product)) {
            Double rating = map.get(product);
            if (rating < (c2pRating * p2pRating)) {
                map.put(product, (c2pRating * p2pRating));
            }
        } else {
            map.put(product, (c2pRating * p2pRating));
        }
    }

    private void calculateP2P(Product source, Product target, Double minRating, Integer percentageRate) {
        Product2ProductRating p2p = p2PDao.findBySourceAndAndTarget(source, target);
        if (Objects.isNull(p2p)) {
            p2p = new Product2ProductRating();
            p2p.setSource(source);
            p2p.setTarget(target);
            p2p.setRating(minRating);
        } else {
            p2p.setRating(calculateNewRating(minRating, percentageRate, p2p.getRating()));
        }
        p2PDao.save(p2p);
    }

    private Double calculateNewRating(Double minRating, Integer percentageRate, Double existingRating) {
        double newRating = (existingRating * 100 + percentageRate) / 100;
        if (newRating < 10) {
            if (newRating < minRating) {
                newRating = (minRating * 100 + percentageRate) / 100;
            }
            return newRating > minRating ? newRating : minRating;
        } else {
            return 9.9;
        }
    }

}
