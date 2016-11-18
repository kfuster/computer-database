package com.excilys.formation.service;

import java.util.List;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;

/**
 * Interface for CompanyServices.
 * @author kfuster
 *
 */
public interface CompanyService extends BaseService<Company> {
    /**
     * Populate a list of Company according to the Page parameters.
     * @param pPageCompany the Page containing the parameters and the list
     * @return the Page with the populated list
     */
    Page<CompanyDto> getPage(Page<CompanyDto> pPageCompany);
    List<CompanyDto> getAll();
}