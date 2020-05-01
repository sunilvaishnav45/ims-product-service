package pdservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdservice.entity.Product;

@Repository
public interface ProductDao extends CrudRepository<Product,Integer> {

}
