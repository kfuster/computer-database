package com.excilys.formation.cli;

import org.slf4j.LoggerFactory;
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
import ch.qos.logback.classic.Logger;

public class Controller {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Controller.class);
    private CompanyService companyService = CompanyServiceImpl.getInstance();
    private ComputerService computerService = ComputerServiceImpl.getInstance();

    /**
     * Get a Page<Company> from the service and converts it to Page<CompanyDto>.
     * @param pPageFilter the PageFilter containing the parameters of the page
     * @return a Page<CompanyDto>
     */
    public Page<CompanyDto> getPageCompany(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            return PageMapper.fromCompanyToCompanyDto(companyService.getPage(pPageFilter));
        }
        return null;
    }

    /**
     * Asks the service to delete a Company.
     * @param pId the id of the Company to delete
     * @return a boolean indicating if the Company was deleted
     */
    public void deleteCompany(long pId) {
        try {
            companyService.delete(pId);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Asks the service to create a Computer. The given ComputerDto will be
     * converted to a Computer then send to the service.
     * @param pComputerDto the ComputerDto containing the informations of the
     *            Computer.
     * @return a ComputerDto
     */
    public ComputerDto createComputer(ComputerDto pComputerDto) {
        Computer computer = DtoMapper.toComputer(pComputerDto);
        try {
            return DtoMapper.fromComputer(computerService.create(computer));
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    /**
     * Asks the service to update a Computer. The given ComputerDto will be
     * converted to a Computer then send to the service.
     * @param pComputerDto
     */
    public void updateComputer(ComputerDto pComputerDto) {
        Computer computer = DtoMapper.toComputer(pComputerDto);
        try {
            computerService.update(computer);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Get a Page<Computer> from the service and converts it to
     * Page<ComputerDto>.
     * @param pPageFilter the PageFilter containing the parameters of the page
     * @return a Page<Computer>
     */
    public Page<ComputerDto> getPageComputer(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            return PageMapper.fromComputerToComputerDto(computerService.getPage(pPageFilter));
        }
        return null;
    }

    /**
     * Get a Computer from the service and converts it to ComputerDto.
     * @param pId the id of the Computer to get
     * @return a ComputerDto
     */
    public ComputerDto getComputerById(long pId) {
        return DtoMapper.fromComputer(computerService.getById(pId));
    }

    /**
     * Asks the service to delete a Computer.
     * @param pId the id of the Computer to delete
     * @return a boolean indicating if the Computer was deleted
     */
    public void deleteComputer(long pId) {
        try {
            computerService.delete(pId);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
        }
    }
}