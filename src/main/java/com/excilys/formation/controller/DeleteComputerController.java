package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.service.ComputerService;

@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController {
    @Autowired
    private ComputerService computerService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView deleteComputer(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("redirect:/dashboard");
        //We get the id list of computers to delete from the parameters
        //and ask the service to delete id
        if (request.getParameter("selection") != null) {
            computerService.deleteList(request.getParameter("selection"));
        }
        
        HttpSession session = request.getSession(false);
        session.setAttribute("deleted", true);
        return model;
    }
}