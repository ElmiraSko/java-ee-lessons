package ru.erasko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.CartService;
import ru.erasko.service.repr.LineItem;

import javax.ejb.Stateful;
import java.math.BigDecimal;
import java.util.*;

@Stateful
public class CartServiceImpl implements CartService {

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private final int sessionId = CartServiceImpl.class.hashCode();

    @Override
    public int getSessionId() {
        return sessionId;
    }

    HashMap<Long, LineItem> lineItems = new HashMap<>();

    @Override
    public void addProduct(LineItem lineItem) {
        if (lineItems.containsKey(lineItem.getId())) {
            lineItem.setQty(lineItems.get(lineItem.getId()).getQty() + 1);
            logger.info(" = " + lineItem.getTotalPrice());
        }
            lineItems.put(lineItem.getId(), lineItem);
    }

    @Override
    public List<LineItem> getAllProduct() {
        List<LineItem> lineItemsList = new ArrayList<>(lineItems.values());
        logger.info("CartServiceImpl, getAllProduct()" + lineItemsList.toString());
        return lineItemsList;
    }

    @Override
    public void delete(LineItem lineItem) {
        lineItems.remove(lineItem.getId());
    }

// cartTotalSum() возвращает итоговую сумму заказа
    @Override
    public BigDecimal cartTotalSum() {
        BigDecimal sum = new BigDecimal(0);
        List<LineItem> lineItemsList = new ArrayList<>(lineItems.values());
        for (LineItem item: lineItemsList) {
            sum = sum.add(item.getTotalPrice());
        }
        return sum;
    }
}
