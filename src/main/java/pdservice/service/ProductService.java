package pdservice.service;

import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.*;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public boolean productExists(Product product);

    public Optional<Product> unAvailableProduct(Product product);

    public Optional<List<Product>> findByIds(List<Integer> ids);

    public Optional<List<Product>> findAll();

    public Optional<Product> updateProduct(Product product);
}
