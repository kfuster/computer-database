package com.excilys.formation.servlet;

import java.io.IOException;
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
        PageFilter pageFilter = RequestMapper.toPageFilter(request);
        Page<ComputerDto> computerPage = PageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));
        this.getServletContext().setAttribute("pageComputer", computerPage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
        if (request.getParameter("selection") != null) {
            computerService.deleteList(request.getParameter("selection"));
        }
        doGet(request, response);
    }
}
