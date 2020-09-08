package ru.erasko.service.interf;

import ru.erasko.service.repr.CategoryRepr;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface CategoryService {

    void insert(CategoryRepr categoryRepr);

    void update(CategoryRepr categoryRepr);

    void delete(Long id);

    Optional<CategoryRepr> findById(Long id);

    List<CategoryRepr> findAll();
}
