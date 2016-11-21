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
    /**
     * 
     */
    private static final long serialVersionUID = 8194132027777240150L;
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
        List<CompanyDto> listCompanies = companyService.getAll();
        this.getServletContext().setAttribute("listCompanies", listCompanies);
        this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/addComputer.jsp" ).forward( request, response );
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Extract datas from the request to a ComputerDto
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String, String> errors = new HashMap<>();
        //Check if datas are valid
        errors = Validator.validateComputerDto(computerDto, errors);
        //If errors found, add errors to the request and go to get instead
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("computerDto", computerDto);
            doGet(request, response);
        }
        //Else create the computer
        ComputerService computerService = ComputerServiceImpl.getInstance();
        try {
            computerService.create(computerDto);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Ordinateur créé');");
            out.println("location='addComputer';");
            out.println("</script>");
        } catch (ServiceException e) {
        }
    }    
}