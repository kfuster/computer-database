package com.excilys.formation.service;

import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;

/**
 * Interface for CompanyServices
 * @author kfuster
 *
 */
public interface CompanyService extends BaseService<Company>{
    /**
     * Populate a list of Company according to the Page parameters
     * @param pPage the Page containing the parameters and the list
     */
    void getPage(Page<Company> pPage);
}
