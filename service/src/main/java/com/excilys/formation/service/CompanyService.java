package com.excilys.formation.service;

import java.util.List;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

/**
 * Interface for CompanyServices.
 * @author kfuster
 *
 */
public interface CompanyService extends BaseService<Company> {
    /**
     * Populate a list of Company according to the PageFilter parameters.
     * @param pPageFilter the PageFilter containing the parameters
     * @return the Page with the populated list
     */
    Page<Company> getPage(PageFilter pPageFilter);

    /**
     * Get all Companies.
     * @return the List of Companies
     */
    List<Company> getAll();
    
    /**
     * Get a Company by it's id.
     * @return a Company or null
     */
    Company getById(long pId);
}