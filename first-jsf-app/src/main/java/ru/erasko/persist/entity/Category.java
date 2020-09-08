package ru.erasko.persist.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL
//            ,
//            orphanRemoval = true // ? с ним возникает ошибка
//            A collection with cascade="all-delete-orphan" was no longer
//            referenced by the owning entity instance: ru.erasko.persist.entity.Category.products
//	at org.hibernate.engine.internal.Collections.processDereferencedCollection(Collections.java:100)
    )
    private List<Product> products;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
