package com.excilys.formation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.implementation.ComputerServiceImpl;

public class DeleteComputerServlet extends HttpServlet {

    private static final long serialVersionUID = -4175989179642460064L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerService computerService = ComputerServiceImpl.getInstance();
        if (request.getParameter("selection") != null) {
            computerService.deleteList(request.getParameter("selection"));
        }
        HttpSession session = request.getSession(false);
        session.setAttribute("deleted", true);
        response.sendRedirect("/dashboard");
    }
}