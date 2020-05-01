package pdservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attributes")
public class Attributes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "attribute")
    @NotNull
    private String attribute;

    @Column(name = "available", columnDefinition = "tinyint(1) default 1")
    private boolean available;
/*

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attributes", cascade = CascadeType.ALL)
    private Set<AttributeValues> attributeValuesSet = new HashSet<AttributeValues>();

*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

   /* public Set<AttributeValues> getUserRoleMappingSet() {
        return attributeValuesSet;
    }

    public void setUserRoleMappingSet(Set<AttributeValues> userRoleMappingSet) {
        this.attributeValuesSet = userRoleMappingSet;
    }*/
}
