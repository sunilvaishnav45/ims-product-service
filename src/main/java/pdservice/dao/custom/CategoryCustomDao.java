package pdservice.dao.custom;

import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryCustomDao {

    private static final Logger LOGGER = Logger.getLogger(CategoryCustomDao.class);

    @Autowired
    private EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<Category> findById(int id) {
        return Optional.ofNullable(getSession().find(Category.class,id));
    }

    public Optional<List<Category>> findAll() {
        Category category = null;
        Query query = entityManager.createNativeQuery("select * from category", Category.class);
        List<Category> categories = query.getResultList();
        return Optional.ofNullable(categories);
    }

    public Optional<Category> findByName(String name) {
        List<Category> categoryList = null;
        Query query = entityManager.createNativeQuery("select * from category where category= ?", Category.class);
        query.setParameter(1,name);
        categoryList =  query.getResultList();
        return (categoryList!=null && !categoryList.isEmpty()) ? Optional.ofNullable(categoryList.get(0)) : Optional.ofNullable(null)   ;
    }
}
