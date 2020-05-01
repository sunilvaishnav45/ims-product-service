package pdservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.Attributes;

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


}
