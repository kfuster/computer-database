package com.excilys.formation.controller;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.NotFoundException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.util.WebUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class for the computer.
 */
@Controller
@RequestMapping("/")
public class ComputerController {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private DtoMapper dtoMapper;

    @Autowired
    private PageMapper pageMapper;

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(@RequestParam Map<String, String> parameters) {
        ModelAndView model = new ModelAndView("/dashboard");
        PageFilter pageFilter = WebUtil.toPageFilter(parameters);
        model.addObject("deleted", null);
        Page<ComputerDto> computerPage = pageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));
        model.addObject("order", parameters.get("order"));
        model.addObject("column", parameters.get("column"));
        model.addObject("pageComputer", computerPage);
        return model;
    }

    @RequestMapping(path = "/addComputer", method = RequestMethod.GET)
    public ModelAndView addComputerGet() {
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        ModelAndView model = new ModelAndView("/addComputer");
        model.addObject("computerDto", new ComputerDto());
        model.addObject("success", null);
        model.addObject("listCompanies", listCompanies);
        return model;
    }

    @RequestMapping(path = "/addComputer", method = RequestMethod.POST)
    public ModelAndView addComputerPost(@Valid @ModelAttribute("computerDto") ComputerDto pComputerDto,
            BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                computerService.create(dtoMapper.toComputer(pComputerDto));
                model.addObject("success", true);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            return model;
        }
        ModelAndView model = new ModelAndView("/addComputer");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        model.addObject("listCompanies", listCompanies);
        return model;
    }

    @RequestMapping(path = "/deleteComputer", method = RequestMethod.GET)
    public ModelAndView deleteComputerGet() {
        ModelAndView model = new ModelAndView("redirect:/dashboard");
        return model;
    }

    @RequestMapping(path = "/deleteComputer", method = RequestMethod.POST)
    public ModelAndView deleteComputerPost(@RequestParam Map<String, String> parameters) {
        ModelAndView model = new ModelAndView("redirect:/dashboard");
        // We get the id list of computers to delete from the parameters
        // and ask the service to delete id
        List<Long> computersId = WebUtil.toListIds(parameters.get("selection"));
        if (computersId != null) {
            computerService.deleteList(computersId);
        }
        return model;
    }

    @RequestMapping(path = "editComputer", params = {"id"}, method = RequestMethod.GET)
    public ModelAndView editComputerGet(@RequestParam(value = "id") Long id) {
        if (id != null) {
            ComputerDto computerDto = dtoMapper.fromComputer(computerService.getById(id));
            if (computerDto == null || computerDto.getName() == null || computerDto.getName().isEmpty()) {
                throw new NotFoundException();
            } else {
                ModelAndView model = new ModelAndView("/editComputer");
                model.addObject("computerDto", computerDto);
                List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
                model.addObject("listCompanies", listCompanies);
                return model;
            }
        }
        return new ModelAndView("redirect:/dashboard");
    }

    @RequestMapping(path = "editComputer", method = RequestMethod.POST)
    public ModelAndView editComputerPost(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                computerService.update(dtoMapper.toComputer(computerDto));
                model.addObject("success", true);
                model.addObject("computerDto", computerDto);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            return model;
        }
        ModelAndView model = new ModelAndView("/editComputer");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        model.addObject("listCompanies", listCompanies);
        return model;
    }

    @RequestMapping(path = "internationalization.js")
    public ModelAndView strings(HttpServletRequest request) {
        // Retrieve the locale of the User
        Locale locale = RequestContextUtils.getLocale(request);
        // Use the path to your bundle
        ResourceBundle bundle = ResourceBundle.getBundle("messages.messages", locale);
        // Call the string.jsp view
        return new ModelAndView("/internationalization", "keys", bundle.getKeys());
    }
}