package pdservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdservice.entity.Brand;

@Repository
public interface BrandDao extends CrudRepository<Brand,Integer> {
}
