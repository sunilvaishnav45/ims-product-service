package pdservice.dao.custom;

import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import pdservice.entity.Brand;
import pdservice.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class BrandCustomDao {

    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(BrandCustomDao.class);

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<Brand> getBrandByName(String brand){
        List<Brand> brands = null;
        Query query = entityManager.createNativeQuery("select * from brand where brand = ?",Brand.class);
        query.setParameter(1,brand);
        brands = query.getResultList();
        return brands!=null && !brands.isEmpty()? Optional.ofNullable(brands.get(0)) : Optional.ofNullable(null);
    }

    @Modifying
    public Optional<Brand> unAvailableBrand(Brand brand){
        Query query = entityManager.createNativeQuery("update brand set available=0 where id = ?",Brand.class);
        query.setParameter(1,brand.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(brand) : Optional.ofNullable(null);
    }

    public Optional<List<Brand>> findAll(){
        List<Brand> brandList = null;
        Query query = entityManager.createNativeQuery("select * from brand where available =1 ",Brand.class);
        brandList = query.getResultList();
        return brandList!=null && !brandList.isEmpty() ? Optional.ofNullable(brandList) : Optional.ofNullable(null);
    }

    public Optional<List<Brand>> findByIds(List<Integer> ids){
        List<Brand> brandList = null;
        Query query = entityManager.createNativeQuery("select * from brand where available =1 AND id IN (?)",Brand.class);
        query.setParameter(1,String.join(",", StringUtils.convertListInto(ids,s -> String.valueOf(s))));
        brandList = query.getResultList();
        return brandList!=null && !brandList.isEmpty() ? Optional.ofNullable(brandList) : Optional.ofNullable(null);
    }

    public Optional<Brand> updateBrand(Brand brand){
        Query query = entityManager.createNativeQuery(
                "update brand set brand=?,available=? where id=?",Brand.class);
        query.setParameter(1,brand.getBrand());
        query.setParameter(2,brand.isAvailable());
        query.setParameter(3,brand.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(brand) : Optional.ofNullable(null);
    }


}
