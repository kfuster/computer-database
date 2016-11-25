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
import org.slf4j.LoggerFactory;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.implementation.CompanyServiceImpl;
import com.excilys.formation.service.implementation.ComputerServiceImpl;
import com.excilys.formation.servlet.validation.Validator;
import ch.qos.logback.classic.Logger;

public class EditComputerServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 7030753372478089174L;
    final Logger logger = (Logger) LoggerFactory.getLogger(EditComputerServlet.class);
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
        ComputerService computerService = ComputerServiceImpl.getInstance();
        String computerId = request.getParameter("id");
        if (computerId != null && !computerId.trim().isEmpty()) {
            ComputerDto computerDto = DtoMapper.fromComputer(computerService.getById(Integer.parseInt(computerId)));
            List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
            this.getServletContext().setAttribute("listCompanies", listCompanies);
            this.getServletContext().setAttribute("computerDto", computerDto);
            this.getServletContext().getRequestDispatcher( "/WEB-INF/jsp/editComputer.jsp" ).forward( request, response );    
        } 
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
        ComputerService computerService = ComputerServiceImpl.getInstance();
        try {
            computerService.update(DtoMapper.toComputer(computerDto));
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Ordinateur mis à jour');");
            out.println("location='editComputer?id="+computerDto.getId()+"';");
            out.println("</script>");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
        }
    }
}