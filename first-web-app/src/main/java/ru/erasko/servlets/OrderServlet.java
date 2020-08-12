package ru.erasko.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.OrderRepository;
import ru.erasko.persist.entity.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@WebServlet(name = "servlets.OrderServlet",
        urlPatterns = {"/order", "/order/new", "/order/edit", "/order/upsert"})
public class OrderServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    private OrderRepository orderRepository;

    public void init()throws ServletException {
        orderRepository = (OrderRepository) getServletContext().getAttribute("orderRepository");
        if (orderRepository == null) {
            throw new ServletException("Order repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Order page");

        if (req.getServletPath().equals("/order")) {
            try {
                req.setAttribute("orders", orderRepository.findAll());
                getServletContext().getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        if (req.getServletPath().equals("/order/new")) {
            req.setAttribute("order", new Order());
            getServletContext().getRequestDispatcher("/WEB-INF/order-form.jsp").forward(req, resp);
        }  else
        if (req.getServletPath().equals("/order/edit")) {
            String id = req.getParameter("id");
            try {
                Optional<Order> opt = orderRepository.findById(Long.parseLong(id));
                if (opt.isPresent()) {
                    req.setAttribute("order", opt.get());
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/order-form.jsp").forward(req, resp);
        }
        else resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/order/upsert")) {
            try {
                String strId = req.getParameter("id");

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String dateInString = req.getParameter("dateParam");
                logger.info("Data = " + dateInString);
                logger.info("== " + sdf.parse(dateInString).getTime());

                if (strId.isEmpty()) {
                    orderRepository.insert(new Order( -1L,
                            Long.parseLong(req.getParameter("customerId")),
                            Integer.parseInt(req.getParameter("qty")),
                            new BigDecimal(req.getParameter("totalPrice")),
                            new Date(sdf.parse(dateInString).getTime())));
                }
                else {
                    orderRepository.update(new Order( Long.parseLong(strId),
                            Long.parseLong(req.getParameter("customerId")),
                            Integer.parseInt(req.getParameter("qty")),
                            new BigDecimal(req.getParameter("totalPrice")),
                            new Date(sdf.parse(dateInString).getTime())));
                }
                resp.sendRedirect(getServletContext().getContextPath() + "/order");

            } catch (SQLException | ParseException ex) {
                throw new IllegalStateException(ex);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
