package com.excilys.formation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.excilys.formation.service.ComputerService;

public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = -4175989179642460064L;
    private ComputerService computerService;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //We get the id list of computers to delete from the parameters
        //and ask the service to delete id
        if (request.getParameter("selection") != null) {
            computerService.deleteList(request.getParameter("selection"));
        }
        //We set an attribute to show a message on the dashboard
        HttpSession session = request.getSession(false);
        session.setAttribute("deleted", true);
        //Finally we redirect on the dashboard
        response.sendRedirect("/dashboard");
    }
    
    @Override
    public void init() {
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        this.computerService = (ComputerService)applicationContext.getBean(ComputerService.class);
    }
}