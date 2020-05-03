package pdservice.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdservice.dao.AttributeValuesDao;
import pdservice.dao.BrandDao;
import pdservice.dao.CategoryDao;
import pdservice.dao.custom.*;
import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.*;
import pdservice.service.ProductService;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCustomDao productCustomDao;

    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    @Override
    public boolean productExists(Product product) {
        return productCustomDao.getProduct(product).isPresent();
    }

    @Override
    public Optional<Product> unAvailableProduct(Product product) {
        return productCustomDao.unAvailableProduct(product);
    }

    @Override
    public Optional<List<Product>> findByIds(List<Integer> ids) {
        return productCustomDao.findByIds(ids);
    }

    @Override
    public Optional<List<Product>> findAll() {
        return productCustomDao.findAll();
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        return productCustomDao.updateProduct(product);
    }
}
