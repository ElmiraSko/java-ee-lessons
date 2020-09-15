package ru.erasko.service.interf;

import ru.erasko.service.repr.CategoryRepr;
import ru.erasko.service.repr.ProductRepr;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProductServiceWs {

    @WebMethod
    List<ProductRepr> findAll();

    @WebMethod
    ProductRepr getProductById(Long id);

    @WebMethod
    ProductRepr getProductByName(String name);

    @WebMethod
    List<ProductRepr> getProductsByCategoryId(Long id);

    @WebMethod
    void insert(ProductRepr productRepr);

    @WebMethod
    void delete(Long id);

    @WebMethod
    void insertCategory(CategoryRepr categoryRepr);
}
