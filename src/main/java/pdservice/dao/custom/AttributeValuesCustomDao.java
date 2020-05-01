package pdservice.dao.custom;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pdservice.entity.AttributeValues;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AttributeValuesCustomDao {

    @Autowired
    private EntityManager entityManager;

    public Session getSession(){
        return entityManager.unwrap(Session.class);
    }

    public Optional<AttributeValues> getAttributeValuesByName(AttributeValues attributeValues){
        List<AttributeValues> attributesValuesList = null;
        Query query = entityManager.createNativeQuery("select * from attribute_values where attributes =? and attribute_value = ?", AttributeValues.class);
        query.setParameter(1,attributeValues.getAttributes().getId());
        query.setParameter(2,attributeValues.getAttributeValue());
        attributesValuesList = query.getResultList();
        return attributesValuesList!=null && !attributesValuesList.isEmpty() ? Optional.ofNullable(attributesValuesList.get(0)) : Optional.ofNullable(null);
    }

}
