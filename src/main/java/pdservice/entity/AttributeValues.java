package pdservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attribute_values")
public class AttributeValues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "attributes")
    @NotNull
    private Attributes attributes;

    @Column(name = "attribute_value")
    @NotNull
    private String attributeValue;

    @Column(name = "available", columnDefinition = "tinyint(1) default 1")
    private boolean available;

   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "attributeValues", cascade = CascadeType.ALL)
    private Set<ProductAttributeValueMapping>  productAttributeValueMappingSet = new HashSet<ProductAttributeValueMapping>();*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
/*
    public Set<ProductAttributeValueMapping> getProductAttributeValueMappingSet() {
        return productAttributeValueMappingSet;
    }

    public void setProductAttributeValueMappingSet(Set<ProductAttributeValueMapping> productAttributeValueMappingSet) {
        this.productAttributeValueMappingSet = productAttributeValueMappingSet;
    }*/
}
