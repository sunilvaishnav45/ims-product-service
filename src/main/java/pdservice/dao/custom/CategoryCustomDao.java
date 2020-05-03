package pdservice.dao.custom;

import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Brand;
import pdservice.entity.Category;
import pdservice.utils.StringUtils;

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

    public Optional<Category> findByName(String name) {
        List<Category> categoryList = null;
        Query query = entityManager.createNativeQuery("select * from category where category= ?", Category.class);
        query.setParameter(1,name);
        categoryList =  query.getResultList();
        return (categoryList!=null && !categoryList.isEmpty()) ? Optional.ofNullable(categoryList.get(0)) : Optional.ofNullable(null)   ;
    }

    public Optional<Category> unAvailableCategory(Category category){
        Query query = entityManager.createNativeQuery("update category set available=0 where id = ?",Category.class);
        query.setParameter(1,category.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(category) : Optional.ofNullable(null);
    }

    public Optional<List<Category>> findAll(){
        List<Category> categoryList = null;
        Query query = entityManager.createNativeQuery("select * from category where available =1 ",Category.class);
        categoryList = query.getResultList();
        return categoryList!=null && !categoryList.isEmpty() ? Optional.ofNullable(categoryList) : Optional.ofNullable(null);
    }

    public Optional<List<Category>> findByIds(List<Integer> ids){
        List<Category> categoryList = null;
        Query query = entityManager.createNativeQuery("select * from category where available =1 AND id IN (?)",Category.class);
        query.setParameter(1,String.join(",", StringUtils.convertListInto(ids, s -> String.valueOf(s))));
        categoryList = query.getResultList();
        return categoryList!=null && !categoryList.isEmpty() ? Optional.ofNullable(categoryList) : Optional.ofNullable(null);
    }

    public Optional<Category> updateCategory(Category category){
        List<Category> categoryList = null;
        Query query = entityManager.createNativeQuery(
                "update category set category=?,available=? where id=?",Category.class);
        query.setParameter(1,category.getCategory());
        query.setParameter(2,category.isAvailable());
        query.setParameter(3,category.getId());
        categoryList = query.getResultList();
        return categoryList!=null && !categoryList.isEmpty() ? Optional.ofNullable(categoryList.get(0)) : Optional.ofNullable(null);
    }
}
