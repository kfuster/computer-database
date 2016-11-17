package com.excilys.formation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerServiceImpl;

public class IndexServlet extends HttpServlet {
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        Page<ComputerDto> pageComputer = new Page<>(10);
        if(request.getParameter("page") != null) {
            pageComputer.setPage(Integer.parseInt(request.getParameter("page")));
        }
        if(request.getParameter("limit") != null) {
            pageComputer.setElemByPage(Integer.parseInt(request.getParameter("limit")));
        }
        pageComputer = computerService.getPage(pageComputer);
        this.getServletContext().setAttribute("computerService", computerService);
        this.getServletContext().setAttribute("pageComputer", pageComputer);
        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/index.jsp" ).forward( request, response );
    }
}
