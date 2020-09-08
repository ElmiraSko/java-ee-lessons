package ru.erasko.controller;

import ru.erasko.persist.CategoryRepository;
import ru.erasko.persist.ProductRepository;
import ru.erasko.persist.entity.Category;
import ru.erasko.persist.entity.Product;
import ru.erasko.service.interf.CategoryService;
import ru.erasko.service.repr.CategoryRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryController implements Serializable {

    @EJB
    private CategoryService categoryService;

    private CategoryRepr categoryRepr;

    public CategoryRepr getCategory() {
        return categoryRepr;
    }

    public void setCategory(CategoryRepr categoryRepr) {
        this.categoryRepr = categoryRepr;
    }

    public List<CategoryRepr> getAllCategories() {
        return categoryService.findAll();
    }

    public String createCategory() {
        this.categoryRepr = new CategoryRepr();
        return "/category-form.xhtml?faces-redirect=true";
    }

    public String editCategory(CategoryRepr category) {
        this.categoryRepr = category;
        return "/category-form.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryRepr category) {
        categoryService.delete(category.getId());
    }

    public String saveCategory() {
        if (categoryRepr.getId() != null) {
            categoryService.update(categoryRepr);
        } else {
            categoryService.insert(categoryRepr);
        }
        return "/category.xhtml?faces-redirect=true";
    }

    public List<CategoryRepr> getCategories() {
        return categoryService.findAll();
    }
}