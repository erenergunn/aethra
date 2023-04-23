package com.eren.aethra.helpers;

import com.eren.aethra.daos.C2PDao;
import com.eren.aethra.daos.P2PDao;
import com.eren.aethra.models.Customer;
import com.eren.aethra.models.Customer2ProductRating;
import com.eren.aethra.models.Product;
import com.eren.aethra.models.Product2ProductRating;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class RatingCalculationHelper {

    @Resource
    P2PDao p2PDao;

    @Resource
    C2PDao c2PDao;

    public void createOrRecalculateRatingOfC2P(Customer customer, Product product, Double minRating, Integer percentageRate) {
        Customer2ProductRating c2p = c2PDao.findByCuAndCustomerAndAndProduct(customer, product);
        if (Objects.isNull(c2p)) {
            c2p = new Customer2ProductRating();
            c2p.setCustomer(customer);
            c2p.setProduct(product);
            c2p.setRating(minRating);
        } else {
            double newRating = (c2p.getRating() * 100 + percentageRate) / 100;
            if (newRating < 10) {
                if (newRating < minRating) {
                    newRating = (minRating * 100 + percentageRate) / 100;
                }
                c2p.setRating(newRating);
            }
        }
        c2PDao.save(c2p);
    }

    public void createOrRecalculateRatingOfP2P(List<Product> products, Product product, Double minRating, Integer percentageRate) {
        products.forEach(p -> {
            if (!p.getCode().equals(product.getCode())) {
                Product2ProductRating p2p = p2PDao.findBySourceAndAndRating(product, p);
                if (Objects.isNull(p2p)) {
                    p2p = new Product2ProductRating();
                    p2p.setSource(product);
                    p2p.setTarget(p);
                    p2p.setRating(minRating);
                } else {
                    double newRating = (p2p.getRating() * 100 + percentageRate) / 100;
                    if (newRating < 10) {
                        if (newRating < minRating) {
                            newRating = (minRating * 100 + percentageRate) / 100;
                        }
                        p2p.setRating(newRating);
                    }
                }
                p2PDao.save(p2p);
            }
        });
    }

}
