package com.excilys.formation.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;

import ch.qos.logback.classic.Logger;

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
        if (id <= 0) {
            return new ResponseEntity<String>("Wrong id", HttpStatus.NOT_ACCEPTABLE);
        }
        
        Company company = companyService.getById(id);
        if( company == null ) {
            return new ResponseEntity<String>("No company found for ID " + id, HttpStatus.NOT_FOUND);
        }
        
        try {
            companyService.delete(id);
        } catch (ServiceException e) {
            LOGGER.error( "CompanyController : delete() catched ServiceException",e);
            return new ResponseEntity<String>("Error while deleting company", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Company deleted", HttpStatus.OK);
    }
}