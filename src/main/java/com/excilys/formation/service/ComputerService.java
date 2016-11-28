package com.excilys.formation.service;

import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

/**
 * Interface for ComputerServices.
 * @author kfuster
 *
 */
public interface ComputerService extends BaseService<Computer> {
    /**
     * Get a computer by its id.
     * @param pId the id of the Computer to get
     * @return the ComputerDto of the computer obtained from the DB
     */
    Computer getById(long pId);

    /**
     * Populate a page of Computer according to the ViewDto parameters.
     * @param pPageFilter the PageFilter containing the parameters
     * @return the Page with the populated list
     */
    Page<Computer> getPage(PageFilter pPageFilter);

    void deleteList(String pListId);
}