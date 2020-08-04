package ru.erasko.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.NotFoundExceptionServlet", urlPatterns = "/exception4")
public class NotFoundExceptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println("<style> h2 {color:red;} </style>");

        resp.getWriter().println("<h1 align=center>404 exception</h1>");
        resp.getWriter().println("<h2 align=left>Failed to load resource: " +
                "<br>the server responded with a status of 404 (Not Found)</h2>");


    }
}

