package com.excilys.formation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.servlet.validation.Validator;
import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(EditComputerController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView editComputerGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/editComputer.jsp");
        String computerId = null;
        if (request.getParameter("id") != null) {
            computerId = request.getParameter("id");
        } else if (request.getAttribute("id") != null) {
            computerId = (String) request.getAttribute("id");
        }
        if (computerId != null && !computerId.trim().isEmpty()) {
            ComputerDto computerDto = DtoMapper.fromComputer(computerService.getById(Long.parseLong(computerId)));
            List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
            model.addObject("listCompanies", listCompanies);
            model.addObject("computerDto", computerDto);
            return model;
        }
        
        return null;        
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView editComputerPost(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/editComputer.jsp");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        // Extract datas from the request to a ComputerDto
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String, String> errors = new HashMap<>();
        // Check if datas are valid
        errors = Validator.validateComputerDto(computerDto, errors);
        model.addObject("success", null);
        // If errors found, add errors to the request and go to get instead
        if (!errors.isEmpty()) {
            model.addObject("errors", errors);
            model.addObject("computerDto", computerDto);
        } else {
            try {
                computerService.update(DtoMapper.toComputer(computerDto));
                model.addObject("success", true);
                model.addObject("computerDto", computerDto);
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
        }
        model.addObject("listCompanies", listCompanies);
        return model;        
    }
}