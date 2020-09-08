package ru.erasko.service;

import ru.erasko.persist.CategoryRepository;
import ru.erasko.persist.entity.Category;
import ru.erasko.service.interf.CategoryService;
import ru.erasko.service.repr.CategoryRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService {

    @EJB
    private CategoryRepository categoryRepository;

    @TransactionAttribute
    @Override
    public void insert(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(), categoryRepr.getName());
        categoryRepository.insert(category);
    }

    @TransactionAttribute
    @Override
    public void update(CategoryRepr categoryRepr) {
        Category category = new Category(categoryRepr.getId(), categoryRepr.getName());
        categoryRepository.update(category);
    }

    @TransactionAttribute
    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public Optional<CategoryRepr> findById(Long id) {
        return categoryRepository.findById(id).map(CategoryRepr::new);
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll()
                .stream().map(CategoryRepr::new)
                .collect(Collectors.toList());
    }
}
