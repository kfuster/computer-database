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
@RequestMapping("/addComputer")
public class AddComputerController{
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(AddComputerController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView addComputerGet(HttpServletRequest request, HttpServletResponse response) {
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/addComputer.jsp");
        model.addObject("computerDto", null);
        model.addObject("success", null);
        model.addObject("errors", null);
        model.addObject("listCompanies", listCompanies);
        return model;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addComputerPost(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/addComputer.jsp");
        //Extract datas from the request to a ComputerDto
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String, String> errors = new HashMap<>();
        // Check if datas are valid
        errors = Validator.validateComputerDto(computerDto, errors);
        // If errors found, add errors to the request and go to get instead
        if (!errors.isEmpty()) {
            model.addObject("errors", errors);
            model.addObject("computerDto", computerDto);
            model.addObject("listCompanies", DtoMapper.fromCompanyList(companyService.getAll()));
        } else {
            // Else create the computer
            try {
                computerService.create(DtoMapper.toComputer(computerDto));
                model.addObject("success", true);
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
        }
        return model;    
    }
}