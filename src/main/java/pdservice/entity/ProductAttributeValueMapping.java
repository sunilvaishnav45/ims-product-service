package pdservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_attribute_value_mapping")
public class ProductAttributeValueMapping {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attribute_value")
    @NotNull
    private AttributeValues attributeValues;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AttributeValues getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(AttributeValues attributeValues) {
        this.attributeValues = attributeValues;
    }
}
