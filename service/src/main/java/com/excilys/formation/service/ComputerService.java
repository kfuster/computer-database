package com.excilys.formation.service;

import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

import java.util.List;

/**
 * Interface for ComputerServices.
 * @author kfuster
 */
public interface ComputerService extends BaseService<Computer> {

    /**
     * Get a computer by its id.
     * @param id the id of the Computer to get
     * @return the ComputerDto of the computer obtained from the DB
     */
    Computer getById(long id);

    /**
     * Populate a page of Computer according to the ViewDto parameters.
     * @param pageFilter the PageFilter containing the parameters
     * @return the Page with the populated list
     */
    Page<Computer> getPage(PageFilter pageFilter);

    /**
     * Delete a list of Computers.
     * @param computersId the list of ids of computers to delete in a String of the form "1,2,3"
     */
    void deleteList(List<Long> computersId);
}