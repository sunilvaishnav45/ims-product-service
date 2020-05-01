package pdservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdservice.entity.AttributeValues;

@Repository
public interface AttributeValuesDao extends CrudRepository<AttributeValues,Integer> {
}
