package com.excilys.formation.service;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.Page;

/**
 * Interface for ComputerServices.
 * @author kfuster
 *
 */
public interface ComputerService extends BaseService<ComputerDto> {
    /**
     * Get a computer by its id.
     * @param pId the id of the Computer to get
     * @return the ComputerDto of the computer obtained from the DB
     */
    ComputerDto getById(int pId);
    /**
     * Populate a list of Computer according to the Page parameters.
     * @param pPage the Page containing the parameters and the list
     * @return the Page with the populated list
     */
    Page<ComputerDto> getPage(Page<ComputerDto> pPage);
    Page<ComputerDto> getPageWithFilter(Page<ComputerDto> pPage, String pFilter);
}