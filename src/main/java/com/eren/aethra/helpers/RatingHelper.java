package com.eren.aethra.helpers;

import com.eren.aethra.daos.C2PDao;
import com.eren.aethra.daos.P2PDao;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Customer2ProductRating;
import com.eren.aethra.models.Product;
import com.eren.aethra.models.Product2ProductRating;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RatingHelper {

    @Resource
    private P2PDao p2PDao;

    @Resource
    private C2PDao c2PDao;

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

        List<Product2ProductRating> p2pList = p2PDao.getProduct2ProductRatingsBySource(product);
        if (p2pList != null && p2pList.size() > 0) {
            p2pList.forEach(p2p -> {
                Product rProduct = p2p.getTarget();
                Customer2ProductRating customer2Product = c2PDao.findCustomer2ProductRatingsByCustomerAndAndProduct(customer, rProduct);
                if (customer2Product == null) {
                    customer2Product = new Customer2ProductRating();
                    double newRating = (minRating / 2) + (p2p.getRating() / 20);
                    customer2Product.setRating(newRating > 10 ? 9.9 : newRating);
                    customer2Product.setProduct(rProduct);
                    customer2Product.setCustomer(customer);
                } else {
                    double newRating = customer2Product.getRating() + (percentageRate / 20);
                    customer2Product.setRating(newRating > 10 ? 9.9 : newRating);
                }
                c2PDao.save(customer2Product);
            });
        }
    }

    public void createOrRecalculateRatingOfP2P(List<Product> products, Product product, Double minRating, Integer percentageRate) {
        products.forEach(p -> {
            if (!p.getCode().equals(product.getCode())) {
                calculateP2P(product, p, minRating, percentageRate);
                calculateP2P(p, product, minRating, percentageRate);
            }
        });
    }

    public List<Product> getRecommendedProductsForCustomer (Customer customer) {
        Set<Customer2ProductRating> c2pRel = c2PDao.findCustomer2ProductRatingsByCustomer(customer);
        if (c2pRel == null || c2pRel.size() == 0) {
            return null;
        }

        Set<Customer2ProductRating> p2pSet = new HashSet<>();

        for (Customer2ProductRating c2p : c2pRel) {
            Product product = c2p.getProduct();
            List<Product2ProductRating> p2pList = p2PDao.getProduct2ProductRatingsBySource(product);
            for (Product2ProductRating p2p : p2pList) {
                addC2PToSet(p2pSet, p2p.getTarget(), c2p.getRating(), p2p.getRating());
            }
            addC2PToSet(p2pSet, c2p.getProduct(), c2p.getRating(), c2p.getRating());
        }

        Set<Customer2ProductRating> sortedRel = p2pSet
                .stream()
                .sorted(Comparator.comparing(Customer2ProductRating::getRating))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return ImmutableSet.copyOf(Iterables.limit(sortedRel, 20)).stream().map(Customer2ProductRating::getProduct).collect(Collectors.toList());
    }

    private void addC2PToSet(Set<Customer2ProductRating> set, Product product, Double c2pRating, Double p2pRating) {
        if (set.stream().noneMatch(c2p -> c2p.getProduct().getCode().equals(product.getCode()))) {
            Customer2ProductRating p2p = new Customer2ProductRating();
            double rating = p2pRating * c2pRating;
            p2p.setRating(rating);
            p2p.setProduct(product);
            set.add(p2p);
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
