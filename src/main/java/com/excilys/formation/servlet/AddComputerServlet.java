package com.excilys.formation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.excilys.formation.servlet.mapper.RequestMapper;
import com.excilys.formation.servlet.validation.Validator;

public class AddComputerServlet extends HttpServlet {
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
        List<CompanyDto> listCompanies = companyService.getAll();
        this.getServletContext().setAttribute("listCompanies", listCompanies);
        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/addComputer.jsp" ).forward( request, response );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String, String> errors = new HashMap<>();
        errors = Validator.validateComputerDto(computerDto, errors);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("", computerDto);
            doGet(request, response);
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