package pdservice.service;

import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.*;

import java.util.List;

public interface ProductService {

    public boolean categoryExists(String categoryName);

    public boolean brandExists(String brandName);

    public boolean productExists(Product product);

    public boolean categoryExists(Category category);

    public boolean brandExists(Brand brand);

    public boolean attributeExists(String attribute);

    public boolean attributeExists(Attributes attribute);

    public boolean attributeValuesExists(AttributeValues attributeValues);

    public AttributeValuesResponse saveAttributeValue(List<AttributeValues> attributeValuesList);

}
