package pdservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Category;
import pdservice.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductCustomDao {

    @Autowired
    private EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<Product> getProduct(Product product){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("select * from product where name = ? AND brand = ? AND category = ?", Product.class);
        query.setParameter(1,product.getName());
        query.setParameter(2,product.getBrand().getId());
        query.setParameter(3,product.getCategory().getId());
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList.get(0)) : Optional.ofNullable(null);
    }

    public Optional<Product> unAvailableProduct(Product product){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("update product set available=0 where = ?", Product.class);
        query.setParameter(1,product.getId());
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList.get(0)) : Optional.ofNullable(null);
    }


    public Optional<List<Product>> findAll(){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("select * from product where attribute =1 ",Product.class);
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList) : Optional.ofNullable(null);
    }

    public Optional<List<Product>> findByIds(List<Integer> ids){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("select * from product where attribute =1 AND id IN (?)",Product.class);
        query.setParameter(1,ids);
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList) : Optional.ofNullable(null);
    }
}
