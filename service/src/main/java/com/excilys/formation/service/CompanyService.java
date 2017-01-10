package com.excilys.formation.service;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

import java.util.List;

/**
 * Interface for CompanyServices.
 * @author kfuster
 */
public interface CompanyService extends BaseService<Company> {
    /**
     * Populate a list of Company according to the PageFilter parameters.
     * @param pageFilter the PageFilter containing the parameters
     * @return the Page with the populated list
     */
    Page<Company> getPage(PageFilter pageFilter);

    /**
     * Get all Companies.
     * @return the List of Companies
     */
    List<Company> getAll();
    
    /**
     * Get a Company by it's id.
     * @return a Company or null
     */
    Company getById(long id);
}