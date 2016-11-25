package com.excilys.formation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.implementation.ComputerServiceImpl;

public class DashboardServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -9054781130738656412L;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        if (request.getParameter("page") != null) {
            pageFilter.setPageNum(Integer.parseInt(request.getParameter("page")));
        }
        if (request.getParameter("limit") != null) {
            pageFilter.setElementsByPage(Integer.parseInt(request.getParameter("limit")));
        }
        this.getServletContext().setAttribute("filter", "");
        if (request.getParameter("search") != null) {
            String search = request.getParameter("search").replace("'","''");
            pageFilter.addCondition("computerName", search);
            pageFilter.addCondition("companyName", search);
            this.getServletContext().setAttribute("filter", search);
        }
        Page<ComputerDto> computerPage = PageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));
        this.getServletContext().setAttribute("pageComputer", computerPage);
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
