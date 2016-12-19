package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController{
    @Autowired
    private ComputerService computerService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/dashboard.jsp");
        
        
        PageFilter pageFilter = RequestMapper.toPageFilter(request);
        HttpSession session = request.getSession(false);
        model.addObject("deleted", null);
        if (session != null && session.getAttribute("deleted") != null) {
            model.addObject("deleted", session.getAttribute("deleted"));
            session.removeAttribute("deleted");
        }
        Page<ComputerDto> computerPage = PageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));

        model.addObject("pageComputer", computerPage);

        return model;
    }
}