package ru.erasko.service.interf;

import ru.erasko.service.repr.LineItem;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface CartService {

   void addProduct(LineItem lineItem);

    List<LineItem> getAllProduct();

    void delete(LineItem lineItem);

    int getSessionId();

    BigDecimal cartTotalSum();
}
