package com.excilys.formation.controller;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.UserDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


/**
 * Created by kfuster on 31/12/2016.
 */
@Controller
@RequestMapping("/")
public class UserController {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private DtoMapper dtoMapper;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public ModelAndView registerGet() {
        ModelAndView model = new ModelAndView("/register");
        model.addObject("userDto", new UserDto());
        return model;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                userService.create(dtoMapper.toUser(userDto));
                model.addObject("success", true);
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
            return model;
        }
        return new ModelAndView("/register");
    }
}
