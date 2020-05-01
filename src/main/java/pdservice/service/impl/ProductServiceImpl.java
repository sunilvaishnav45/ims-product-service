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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryCustomDao categoryDaoImpl;

    @Autowired
    private BrandCustomDao brandDaoImpl;

    @Autowired
    private ProductCustomDao productDaoImpl;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttributeCustomDao attributeCustomDao;

    @Autowired
    private AttributeValuesCustomDao attributeValuesCustomDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    @Override
    public boolean categoryExists(String categoryName) {
        return categoryDaoImpl.findByName(categoryName).isPresent();
    }

    @Override
    public boolean brandExists(String brandName) {
        return brandDaoImpl.getBrandByName(brandName).isPresent();
    }

    @Override
    public boolean productExists(Product product) {
        return productDaoImpl.getProduct(product).isPresent();
    }

    @Override
    public boolean categoryExists(Category category) {
        return categoryDao.existsById(category.getId());
    }

    @Override
    public boolean brandExists(Brand brand) {
        return brandDao.existsById(brand.getId());
    }

    @Override
    public boolean attributeExists(String attribute) {
        return attributeCustomDao.getAttributeByName(attribute).isPresent();
    }

    @Override
    public boolean attributeExists(Attributes attribute) {
        return attributeCustomDao.getAttribute(attribute).isPresent();
    }

    @Override
    public boolean attributeValuesExists(AttributeValues attributeValues) {
        return attributeValuesCustomDao.getAttributeValuesByName(attributeValues).isPresent();
    }

    @Override
    public AttributeValuesResponse saveAttributeValue(List<AttributeValues> attributeValuesList) {
        AttributeValuesResponse attributeValuesResponse = new AttributeValuesResponse();
        List<AttributeValues> savedAttributeValues = new ArrayList<AttributeValues>();
        List<AttributeValues> failedAttribute = new ArrayList<AttributeValues>();
        List<AttributeValues> failedAttributeValues = new ArrayList<AttributeValues>();
        attributeValuesList.forEach( attributeValue -> {
            if(!attributeExists(attributeValue.getAttributes()))
                failedAttribute.add(attributeValue);
            else if(attributeValuesExists(attributeValue))
                failedAttributeValues.add(attributeValue);
            else{
                attributeValuesDao.save(attributeValue);
                savedAttributeValues.add(attributeValue);
            }
        });
        attributeValuesResponse.setSavedAttributes(savedAttributeValues);
        Map<String, List<AttributeValues>> failedAttributeValuesMap = new HashMap<String, List<AttributeValues>>();
        failedAttributeValuesMap.put("attributeNotExist",failedAttribute);
        failedAttributeValuesMap.put("attributeValueAlreadyExist",failedAttributeValues);
        attributeValuesResponse.setFailedAttributes(failedAttributeValuesMap);
        return attributeValuesResponse;
    }
}
