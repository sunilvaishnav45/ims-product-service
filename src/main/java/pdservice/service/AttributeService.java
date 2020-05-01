package pdservice.service;

import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.AttributeValues;
import pdservice.entity.Attributes;

import java.util.List;
import java.util.Optional;

public interface AttributeService {

    public boolean attributeExists(String attribute);

    public boolean attributeExists(Attributes attribute);

    public boolean attributeValuesExists(AttributeValues attributeValues);

    public AttributeValuesResponse saveAttributeValue(List<AttributeValues> attributeValuesList);

    public Optional<AttributeValues> unAvailableAttributeValue(AttributeValues attributeValues);

    public Optional<Attributes> unAvailableAttribute(Attributes attributes);

    public Optional<List<Attributes>> findAttributesByIds(List<Integer> ids);

    public Optional<List<AttributeValues>> findAttributeValuesByIds(List<Integer> ids);

    public Optional<List<Attributes>> findAllAttributes();

    public Optional<List<AttributeValues>> findAllAttributeValues();
}
