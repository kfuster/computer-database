package com.excilys.formation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.implementation.ComputerServiceImpl;

public class DashboardServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -9054781130738656412L;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        Page<ComputerDto> pageComputer = new Page<>(10);
        if (request.getParameter("page") != null) {
            pageComputer.setPage(Integer.parseInt(request.getParameter("page")));
        }
        if (request.getParameter("limit") != null) {
            pageComputer.setElemByPage(Integer.parseInt(request.getParameter("limit")));
        }
        String searchFilter = null;
        this.getServletContext().setAttribute("filter", "");
        if (request.getParameter("search") != null) {
            String search = request.getParameter("search").replace("'","''");
            this.getServletContext().setAttribute("filter", search);
            searchFilter = "WHERE computer.name LIKE '%" + search + "%' OR company.name LIKE '%" + search + "%' ";
        }
        pageComputer = computerService.getPageWithFilter(pageComputer, searchFilter);
        this.getServletContext().setAttribute("pageComputer", pageComputer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        boolean deleted = computerService.deleteList(RequestMapper.toIdList(request));
        if(deleted) {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Ordinateur(s) supprimé(s)');");
            out.println("location='dashboard';");
            out.println("</script>");
        }
        else {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Ordinateur(s) non supprimé(s)');");
            out.println("location='dashboard';");
            out.println("</script>");
        }
        doGet(request, response);
    }
}
