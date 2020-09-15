package ru.erasko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.CategoryRepository;
import ru.erasko.persist.ProductRepository;
import ru.erasko.persist.entity.Category;
import ru.erasko.persist.entity.Product;
import ru.erasko.rest.ProductServiceRs;
import ru.erasko.service.interf.ProductService;
import ru.erasko.service.interf.ProductServiceWs;
import ru.erasko.service.repr.CategoryRepr;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.jws.WebService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "ru.erasko.service.interf.ProductServiceWs", serviceName = "ProductService")
public class ProductServiceImpl implements ProductService, ProductServiceWs, ProductServiceRs {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @EJB
    private ProductRepository productRepository;
    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);
        productRepository.insert(product);
    }

    @Override
    public void insertCategoryRs(CategoryRepr categoryRepr) {
        insertCategory(categoryRepr);
    }

    @TransactionAttribute
    @Override
    public void update(ProductRepr productRepr) {
        Category category = categoryRepository.findById(productRepr.getCategoryId())
                .orElse(null);
        Product product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getDescription(),
                productRepr.getPrice(),
                category);
        productRepository.update(product);
    }

    @TransactionAttribute
    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductRepr::new);
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductRepr findByIdRs(Long id) {
        return findById(id).get();
    }

    @Override
    public ProductRepr findByNameRs(String name) {
        return getProductByName(name);
    }

    @Override
    public List<ProductRepr> findByCategoryIdRs(Long id) {
        return getProductsByCategoryId(id);
    }

    @Override
    public ProductRepr getProductById(Long id) {
        return new ProductRepr(productRepository.findById(id).get());
    }

    @Override
    public ProductRepr getProductByName(String name) {
        return new ProductRepr(productRepository.findByName(name));
    }

    @Override
    public List<ProductRepr> getProductsByCategoryId(Long id) {
        return productRepository.findProductByCategoryId(id).stream()
                .map(ProductRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public void insertCategory(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(), categoryRepr.getName());
        categoryRepository.insert(category);
    }

}
