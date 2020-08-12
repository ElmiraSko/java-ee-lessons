package ru.erasko.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.CartItem;
import ru.erasko.persist.CartItemRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "servlets.CartServlet",
        urlPatterns = {"/cart", "/cart/new", "/cart/edit", "/cart/cartUpsert"})
public class CartServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CartServlet.class);

    private CartItemRepository cartRepository;

    public void init()throws ServletException {
        cartRepository = (CartItemRepository) getServletContext().getAttribute("cartRepository");
        if (cartRepository == null) {
            throw new ServletException("CartItem Repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Cart item page");

        if (req.getServletPath().equals("/cart")) {
            try {
                req.setAttribute("cartItems", cartRepository.findAll());
                getServletContext().getRequestDispatcher("/WEB-INF/cartItems.jsp").forward(req, resp);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        if (req.getServletPath().equals("/cart/new")) {
            req.setAttribute("cartItem", new CartItem());
            getServletContext().getRequestDispatcher("/WEB-INF/cart-form.jsp").forward(req, resp);
        }  else
        if (req.getServletPath().equals("/cart/edit")) {
            String id = req.getParameter("id");
            try {
                Optional<CartItem> opt = cartRepository.findById(Long.parseLong(id));
                if (opt.isPresent()) {
                    req.setAttribute("cartItem", opt.get());
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/cart-form.jsp").forward(req, resp);
        }
        else {
            logger.error("Error!!!");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/cart/cartUpsert")) {
            try {
                String strId = req.getParameter("id");
                if (strId.isEmpty()) {
                    cartRepository.insert(new CartItem( -1L,
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("qty")),
                            new BigDecimal(req.getParameter("price"))));
                }
                else {
                    cartRepository.update(new CartItem( Long.parseLong(strId),
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("qty")),
                            new BigDecimal(req.getParameter("price"))));
                }
                resp.sendRedirect(getServletContext().getContextPath() + "/cart");

            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
