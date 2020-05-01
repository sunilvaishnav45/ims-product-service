package pdservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdservice.entity.Attributes;

@Repository
public interface AttributeDao extends CrudRepository<Attributes,Integer> {
}
