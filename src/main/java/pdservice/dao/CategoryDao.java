package pdservice.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pdservice.entity.Category;

@Repository
public interface CategoryDao extends CrudRepository<Category,Integer>{

}
