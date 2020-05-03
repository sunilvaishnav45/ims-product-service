package pdservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Attributes;
import pdservice.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AttributeCustomDao {

    @Autowired
    private EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<Attributes> getAttributeByName(String name){
        List<Attributes> attributesList = null;
        Query query = entityManager.createNativeQuery("select * from attributes where attribute =?",Attributes.class);
        query.setParameter(1,name);
        attributesList = query.getResultList();
        return attributesList!=null && !attributesList.isEmpty() ? Optional.ofNullable(attributesList.get(0)) : Optional.ofNullable(null);
    }

    public Optional<Attributes> getAttribute(Attributes attributes){
        List<Attributes> attributesList = null;
        Query query = entityManager.createNativeQuery("select * from attributes where attribute =? AND id = ?",Attributes.class);
        query.setParameter(1,attributes.getAttribute());
        query.setParameter(2,attributes.getId());
        attributesList = query.getResultList();
        return attributesList!=null && !attributesList.isEmpty() ? Optional.ofNullable(attributesList.get(0)) : Optional.ofNullable(null);
    }

    public Optional<Attributes> unAvailableAttributes(Attributes attributes){
        Query query = entityManager.createNativeQuery("update attributes set available=0 where id =? ",Attributes.class);
        query.setParameter(1,attributes.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(attributes) : Optional.ofNullable(null);
    }

    public Optional<List<Attributes>> findAll(){
        List<Attributes> attributesList = null;
        Query query = entityManager.createNativeQuery("select * from attributes where available =1 ",Attributes.class);
        attributesList = query.getResultList();
        return attributesList!=null && !attributesList.isEmpty() ? Optional.ofNullable(attributesList) : Optional.ofNullable(null);
    }

    public Optional<List<Attributes>> findByIds(List<Integer> ids){
        List<Attributes> attributesList = null;
        Query query = entityManager.createNativeQuery("select * from attributes where available =1 AND id IN (?)",Attributes.class);
        query.setParameter(1,String.join(",", StringUtils.convertListInto(ids, s -> String.valueOf(s))));
        attributesList = query.getResultList();
        return attributesList!=null && !attributesList.isEmpty() ? Optional.ofNullable(attributesList) : Optional.ofNullable(null);
    }

    public Optional<Attributes> updateAttribute(Attributes attributes){
        Query query = entityManager.createNativeQuery("update attributes set attribute=? , available=?  where id =? ",Attributes.class);
        query.setParameter(1,attributes.getAttribute());
        query.setParameter(2,attributes.isAvailable());
        query.setParameter(3,attributes.getId());
        int rowCount = query.executeUpdate();
        return rowCount>0 ? Optional.ofNullable(attributes) : Optional.ofNullable(null);
    }

}
