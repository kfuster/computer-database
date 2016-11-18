package com.excilys.formation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.service.CompanyServiceImpl;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.ComputerServiceImpl;

public class AddComputerServlet extends HttpServlet {
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
        List<CompanyDto> listCompanies = companyService.getAll();
        this.getServletContext().setAttribute("listCompanies", listCompanies);
        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/addComputer.jsp" ).forward( request, response );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyId");
        if (!name.isEmpty()) {
            ComputerDto computerDto = new ComputerDto();
            computerDto.name = name;
            if (!introduced.isEmpty()) {
                computerDto.introduced = LocalDate.parse(introduced);
            }
            if (!discontinued.isEmpty()) {
                computerDto.discontinued = LocalDate.parse(discontinued);
            }
            if (!companyId.isEmpty()) {
                int idCompany = Integer.parseInt(companyId);
                if (idCompany != 0) {
                    computerDto.companyId = idCompany;
                }
            }
            ComputerService computerService = ComputerServiceImpl.getInstance();
            try {
                computerService.create(computerDto);
            } catch (ServiceException e) {
            }
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Ordinateur créé');");
            out.println("location='addComputer';");
            out.println("</script>");
        }
    }    
}