package pdservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Category;
import pdservice.entity.Product;
import pdservice.utils.StringUtils;

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
        Query query = entityManager.createNativeQuery("update product set available=0 where id = ?", Product.class);
        query.setParameter(1,product.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(product) : Optional.ofNullable(null);
    }


    public Optional<List<Product>> findAll(){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("select * from product where available =1 ",Product.class);
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList) : Optional.ofNullable(null);
    }

    public Optional<List<Product>> findByIds(List<Integer> ids){
        List<Product> productList = null;
        Query query = entityManager.createNativeQuery("select * from product where available =1 AND id IN (?)",Product.class);
        query.setParameter(1,String.join(",", StringUtils.convertListInto(ids, s -> String.valueOf(s))));
        productList = query.getResultList();
        return productList!=null && !productList.isEmpty() ? Optional.ofNullable(productList) : Optional.ofNullable(null);
    }

    public Optional<Product> updateProduct(Product product){
        Query query = entityManager.createNativeQuery(
                "update product set name=?, img_url=?, brand=?, category=?, description=?, price=?, rating=?  where id = ?", Product.class);
        query.setParameter(1,product.getName());
        query.setParameter(2,product.getImgURL());
        query.setParameter(3,product.getBrand().getId());
        query.setParameter(4,product.getCategory().getId());
        query.setParameter(5,product.getDescription());
        query.setParameter(6,product.getPrice());
        query.setParameter(7,product.getRating());
        query.setParameter(8,product.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(product) : Optional.ofNullable(null);
    }
}
