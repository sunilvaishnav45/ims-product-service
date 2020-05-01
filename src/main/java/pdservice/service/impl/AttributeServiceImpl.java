package pdservice.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdservice.dao.AttributeValuesDao;
import pdservice.dao.custom.*;
import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.AttributeValues;
import pdservice.entity.Attributes;
import pdservice.service.AttributeService;

import java.util.*;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeCustomDao attributeCustomDao;

    @Autowired
    private AttributeValuesCustomDao attributeValuesCustomDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    private static final Logger LOGGER = Logger.getLogger(AttributeServiceImpl.class);

    @Override
    public Optional<AttributeValues> unAvailableAttributeValue(AttributeValues attributeValues) {
        return attributeValuesCustomDao.unAvailableAttributeValue(attributeValues);
    }

    @Override
    public Optional<Attributes> unAvailableAttribute(Attributes attributes) {
        return attributeCustomDao.unAvailableAttributes(attributes);
    }

    @Override
    public Optional<List<Attributes>> findAttributesByIds(List<Integer> ids) {
        return attributeCustomDao.findByIds(ids);
    }

    @Override
    public Optional<List<AttributeValues>> findAttributeValuesByIds(List<Integer> ids) {
        return attributeValuesCustomDao.findByIds(ids);
    }

    @Override
    public Optional<List<Attributes>> findAllAttributes() {
        return attributeCustomDao.findAll();
    }

    @Override
    public Optional<List<AttributeValues>> findAllAttributeValues() {
        return attributeValuesCustomDao.findAll();
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
}
