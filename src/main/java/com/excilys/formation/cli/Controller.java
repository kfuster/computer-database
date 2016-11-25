package com.excilys.formation.cli;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.implementation.CompanyServiceImpl;
import com.excilys.formation.service.implementation.ComputerServiceImpl;

public class Controller {
    private CompanyService companyService = CompanyServiceImpl.getInstance();
    private ComputerService computerService = ComputerServiceImpl.getInstance();
    public Page<CompanyDto> getPageCompany(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            return PageMapper.fromCompanyToCompanyDto(companyService.getPage(pPageFilter));
        }
        return null;
    }
    
    public boolean deleteCompany(long pId) {
        try {
            return companyService.delete(pId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ComputerDto createComputer(ComputerDto pComputerDto) {
        Computer computer = DtoMapper.toComputer(pComputerDto);
        try {
            return DtoMapper.fromComputer(computerService.create(computer));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateComputer(ComputerDto pComputerDto) {
        Computer computer = DtoMapper.toComputer(pComputerDto);
        try {
            computerService.update(computer);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    public Page<ComputerDto> getPageComputer(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            return PageMapper.fromComputerToComputerDto(computerService.getPage(pPageFilter));
        }
        return null;
    }
    public ComputerDto getComputerById(long pId) {
        return DtoMapper.fromComputer(computerService.getById(pId));
    }
    public boolean deleteComputer(long pId) {
        try {
            return computerService.delete(pId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return false;
    }
}
