package com.excilys.formation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerServiceImpl;

public class DashboardServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -9054781130738656412L;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        Page<ComputerDto> pageComputer = new Page<>(10);
        if (request.getParameter("page") != null) {
            pageComputer.setPage(Integer.parseInt(request.getParameter("page")));
        }
        if (request.getParameter("limit") != null) {
            pageComputer.setElemByPage(Integer.parseInt(request.getParameter("limit")));
        }
        String searchFilter = null;
        if (request.getParameter("search") != null) {
            String search = request.getParameter("search");
            this.getServletContext().setAttribute("filter", search);
            searchFilter = "WHERE computer.name like '%"+search+"%' OR company.name like '%"+search+"%' ";
        }
        pageComputer = computerService.getPageWithFilter(pageComputer, searchFilter);
        this.getServletContext().setAttribute("computerService", computerService);
        this.getServletContext().setAttribute("pageComputer", pageComputer);
        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/dashboard.jsp" ).forward( request, response );
    }
}
