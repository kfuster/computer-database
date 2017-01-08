package com.excilys.formation.controller;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/rest")
public class CompanyController {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private CompanyService companyService;
    
    @RequestMapping(value = "/companies/{limit}/{pagenum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Company>> page(@PathVariable int limit, @PathVariable int pagenum) {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(limit);
        pageFilter.setPageNum(pagenum);
        return new ResponseEntity<>(companyService.getPage(pageFilter), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", LocaleContextHolder.getLocale());
        if (id <= 0) {
            return new ResponseEntity<String>(messages.getString("message.unauthorizedId"), HttpStatus.NOT_ACCEPTABLE);
        }
        
        Company company = companyService.getById(id);
        if( company == null ) {
            return new ResponseEntity<String>(messages.getString("message.companyNotFoundId") + id, HttpStatus.NOT_FOUND);
        }

        companyService.delete(id);
        return new ResponseEntity<String>(messages.getString("error.companyDeleted"), HttpStatus.OK);
    }
}