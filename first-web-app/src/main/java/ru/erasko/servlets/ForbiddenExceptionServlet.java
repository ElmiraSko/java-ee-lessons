package ru.erasko.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.ForbiddenExceptionServlet", urlPatterns = "/exception3")
public class ForbiddenExceptionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().println("<style> h2 {color:red;} </style>");
        resp.getWriter().println("<h1 align=center>403 exception</h1>");
        resp.getWriter().println("<h2 align=left>You do not have permission to access on this server(Forbidden)</h2>");

    }
}
