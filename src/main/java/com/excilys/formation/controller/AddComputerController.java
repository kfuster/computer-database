package com.excilys.formation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
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
        model.addObject("computerDto", new ComputerDto());
        model.addObject("success", null);
        model.addObject("listCompanies", listCompanies);
        return model;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addComputerPost(@Valid @ModelAttribute("computerDto")ComputerDto pComputerDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                computerService.create(DtoMapper.toComputer(pComputerDto));
                model.addObject("success", true); 
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
            return model;
        }
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/addComputer.jsp");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        model.addObject("listCompanies", listCompanies);
        return model;    
    }
}