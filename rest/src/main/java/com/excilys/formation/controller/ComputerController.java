package com.excilys.formation.controller;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;

import java.util.ResourceBundle;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Ookami on 03/01/2017.
 */
@RestController
@RequestMapping(value = "/rest")
public class ComputerController {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerController.class);
    @Autowired
    private ComputerService computerService;

    @RequestMapping(value = "/computers/{id}", method = RequestMethod.GET)
    public ComputerDto computer(@PathVariable Long id) {
        DtoMapper dtoMapper = new DtoMapper();
        Computer computer = computerService.getById(id);
        if( computer == null ) {
            return null;
        }
        return dtoMapper.fromComputer(computer);
    }

    @RequestMapping(value = "/computers/{limit}/{pagenum}", method = RequestMethod.GET)
    public Page<Computer> page(@PathVariable int limit, @PathVariable int pagenum) {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(limit);
        pageFilter.setPageNum(pagenum);
        return computerService.getPage(pageFilter);
    }

    @RequestMapping(value = "/computers", method = RequestMethod.POST)
    public ComputerDto add(@RequestBody ComputerDto computer) {
        DtoMapper dtoMapper = new DtoMapper();
        Computer createdComputer = null;
        try {
            createdComputer = computerService.create(dtoMapper.toComputer(computer));
        } catch (ServiceException e) {
            LOGGER.error( "ComputerController : add() catched ServiceException",e);
            return null;
        }
        return dtoMapper.fromComputer(createdComputer);
    }
    
    @RequestMapping(value = "/computers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", LocaleContextHolder.getLocale());
        if (id <= 0) {
            return new ResponseEntity<String>(messages.getString("message.unauthorizedId"), HttpStatus.NOT_ACCEPTABLE);
        }
        Computer computer = computerService.getById(id);
        if( computer == null ) {
            return new ResponseEntity<String>(messages.getString("message.computerNotFoundId") + id, HttpStatus.NOT_FOUND);
        }
        
        try {
            computerService.delete(id);
        } catch (ServiceException e) {
            LOGGER.error( "ComputerController : delete() catched ServiceException",e);
            return new ResponseEntity<String>(messages.getString("error.deletingComputer"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(messages.getString("message.deleted"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/computers", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody ComputerDto computer) {
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", LocaleContextHolder.getLocale());
        DtoMapper dtoMapper = new DtoMapper();
        try {
            computerService.update(dtoMapper.toComputer(computer));
        } catch (ServiceException e) {
            LOGGER.error( "ComputerController : update() catched ServiceException",e);
            return new ResponseEntity<String>(messages.getString("error.updatingComputer"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(messages.getString("message.updated"), HttpStatus.OK);
    }
}
