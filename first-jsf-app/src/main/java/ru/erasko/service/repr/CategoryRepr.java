package ru.erasko.service.repr;

import ru.erasko.persist.entity.Category;

public class CategoryRepr {

    private Long id;
    private String name;

    public CategoryRepr() {
    }

    public CategoryRepr(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryRepr(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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
}
