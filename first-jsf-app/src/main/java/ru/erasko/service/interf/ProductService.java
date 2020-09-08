package ru.erasko.service.interf;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface ProductService {

    void insert(ProductRepr product);

    void update(ProductRepr product);

    void delete(Long id);

    Optional<ProductRepr> findById(Long id);

   List<ProductRepr> findAll();

}
