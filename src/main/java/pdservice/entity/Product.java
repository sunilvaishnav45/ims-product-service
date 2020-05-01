package pdservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "img_url")
    private String imgURL;

    @ManyToOne
    @JoinColumn(name = "brand")
    @NotNull
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category")
    @NotNull
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "rating")
    private int rating;

    @Column(name = "available", columnDefinition = "tinyint(1) default 1")
    private  boolean available;

   /* @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductAttributeValueMapping> productAttributeValueMappingSet = new HashSet<ProductAttributeValueMapping>();*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

   /* public Set<ProductAttributeValueMapping> getProductAttributeValueMappingSet() {
        return productAttributeValueMappingSet;
    }

    public void setProductAttributeValueMappingSet(Set<ProductAttributeValueMapping> productAttributeValueMappingSet) {
        this.productAttributeValueMappingSet = productAttributeValueMappingSet;
    }*/
}
