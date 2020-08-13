package ru.erasko.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.erasko.persist.CatalogRepository;
import ru.erasko.persist.entity.Catalog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "servlets.CatalogServlet",
        urlPatterns = {"/catalog", "/catalog/new", "/catalog/edit", "/catalog/catalogPost", "/catalog/delete"})
public class CatalogServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CatalogServlet.class);
    private CatalogRepository catalogRepository;

    public void init()throws ServletException {
        catalogRepository = (CatalogRepository) getServletContext().getAttribute("catalogRepo");
        if (catalogRepository == null) {
            throw new ServletException("Catalog Repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CatalogRepository page");

        if (req.getServletPath().equals("/catalog")) {
            try {
                req.setAttribute("catalogs", catalogRepository.findAll());
                getServletContext().getRequestDispatcher("/WEB-INF/catalogs.jsp").forward(req, resp);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        } else
        if (req.getServletPath().equals("/catalog/new")) {
            req.setAttribute("catalog", new Catalog());
            getServletContext().getRequestDispatcher("/WEB-INF/catalog-form.jsp").forward(req, resp);
        }  else
        if (req.getServletPath().equals("/catalog/edit")) {
            String id = req.getParameter("id");
            try {
                Optional<Catalog> opt = catalogRepository.findById(Long.parseLong(id));
                if (opt.isPresent()) {
                    req.setAttribute("catalog", opt.get());
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/catalog-form.jsp").forward(req, resp);
        } else
        if (req.getServletPath().equals("/catalog/delete")) {
            try {
                catalogRepository.delete(Long.parseLong(req.getParameter("id")));
            } catch (SQLException ex) {
                logger.info("SQLException: IllegalStateException(ex) for delete!");
                throw new IllegalStateException(ex);
            }
            logger.info("Delete catalog by id!");
//            getServletContext().getRequestDispatcher("/WEB-INF/catalogs.jsp").forward(req, resp);
            resp.sendRedirect(getServletContext().getContextPath() + "/catalog");
        }
        else {
            logger.error("Error!!!");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/catalog/catalogPost")) {
            try {
                String strId = req.getParameter("id");
                if (strId.isEmpty()) {
                    catalogRepository.insert(new Catalog( -1L,
                            req.getParameter("name")));
                }
                else {
                    catalogRepository.update(new Catalog( Long.parseLong(strId),
                            req.getParameter("name")));
                }
                resp.sendRedirect(getServletContext().getContextPath() + "/catalog");

            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
