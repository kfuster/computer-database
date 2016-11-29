package com.excilys.formation.servlet;

import java.io.IOException;
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
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.implementation.CompanyServiceImpl;
import com.excilys.formation.service.implementation.ComputerServiceImpl;
import com.excilys.formation.servlet.validation.Validator;
import ch.qos.logback.classic.Logger;

public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 8194132027777240150L;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AddComputerServlet.class);
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        request.setAttribute("computerDto", null);
        request.setAttribute("success", null);
        request.setAttribute("errors", null);
        this.getServletContext().setAttribute("listCompanies", listCompanies);
        this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract datas from the request to a ComputerDto
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String, String> errors = new HashMap<>();
        // Check if datas are valid
        errors = Validator.validateComputerDto(computerDto, errors);
        // If errors found, add errors to the request and go to get instead
        request.setAttribute("success", null);
        request.setAttribute("errors", null);
        request.setAttribute("computerDto", null);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("computerDto", computerDto);
            doGet(request, response);
        } else {
            // Else create the computer
            ComputerService computerService = ComputerServiceImpl.getInstance();
            try {
                computerService.create(DtoMapper.toComputer(computerDto));
                request.setAttribute("success", true);
                this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request,
                        response);
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }
}