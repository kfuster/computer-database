package com.excilys.formation.service;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;

/**
 * Interface for ComputerServices
 * @author kfuster
 *
 */
public interface ComputerService extends BaseService<Computer> {
    /**
     * Get a computer by its id
     * @param pId the id of the Computer to get
     * @return
     */
    Computer getById(int pId);
    
    /**
     * Populate a list of Company according to the Page parameters
     * @param pPage the Page containing the parameters and the list
     */
    void getPage(Page<Computer> pPage);
}
