package ru.erasko.controller;

import ru.erasko.persist.CategoryRepository;
import ru.erasko.persist.ProductRepository;
import ru.erasko.persist.entity.Category;
import ru.erasko.persist.entity.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {

    @Inject
    private CategoryRepository categoryRepository;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String createCategory() {
        this.category = new Category();
        return "/category-form.xhtml?faces-redirect=true";
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category-form.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category.getId());
    }

    public String saveCategory() {
        if (category.getId() != null) {
            categoryRepository.update(category);
        } else {
            categoryRepository.insert(category);
        }
        return "/category.xhtml?faces-redirect=true";
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}