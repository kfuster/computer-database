package com.excilys.formation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = -9054781130738656412L;
    private ComputerService computerService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PageFilter pageFilter = RequestMapper.toPageFilter(request);
        HttpSession session = request.getSession(false);
        this.getServletContext().setAttribute("deleted", null);
        if (session != null && session.getAttribute("deleted") != null) {
            this.getServletContext().setAttribute("deleted", session.getAttribute("deleted"));
            session.removeAttribute("deleted");
        }
        Page<ComputerDto> computerPage = PageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));
        this.getServletContext().setAttribute("pageComputer", computerPage);
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }
    
    @Override
    public void init() {
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        this.computerService = (ComputerService)applicationContext.getBean("computerService");
    }
}