package ru.erasko.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.NavBarServlet", urlPatterns = "/navBar")
public class NavBarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<style> a {text-decoration: none; font-size: 22px;} </style>");
        resp.getWriter().println("<style> div {margin-left: 100px;} </style>");

        resp.getWriter().println("<h1 align=center>" + req.getAttribute("page")+ "</h1>");

        resp.getWriter().println("<div>");
        resp.getWriter().println("<h2>Menu:</h2>");
        resp.getWriter().println("<a href='catalog'>Catalog</a><br>");
        resp.getWriter().println("<a href='product'>Product</a><br>");
        resp.getWriter().println("<a href='cart'>Cart</a><br>");
        resp.getWriter().println("<a href='order'>Order</a>");
        resp.getWriter().println("</div>");
    }
}
