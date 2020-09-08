package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.CategoryService;
import ru.erasko.service.interf.ProductService;
import ru.erasko.service.repr.CategoryRepr;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named("productController")
public class ProductController implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @EJB
    private ProductService productService;
    @EJB
    private CategoryService categoryService;

    private ProductRepr productRepr;

    public ProductRepr getProduct() {
        return productRepr;
    }

    public void setProduct(ProductRepr productRepr) {
        this.productRepr = productRepr;
    }

    public List<ProductRepr> getAllProducts(){
        logger.info("logger getAllProducts ");
        return productService.findAll();
    }

    public String createProduct() {
        this.productRepr = new ProductRepr();
        return "/product.xhtml?faces-redirect=true";
    }

    public String editProduct(ProductRepr productRepr) {
        this.productRepr = productRepr;
        return "/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductRepr productRepr) {
        productService.delete(productRepr.getId());
    }

    public String saveProduct() {
        if (productRepr.getId() != null) {
            productService.update(productRepr);
        } else {
            productService.insert(productRepr);
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public List<CategoryRepr> getAllCategories() {
        return categoryService.findAll();
    }
}
