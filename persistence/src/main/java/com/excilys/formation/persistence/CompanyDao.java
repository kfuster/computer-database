package com.excilys.formation.persistence;

import com.excilys.formation.model.Company;

import java.util.List;

public interface CompanyDao extends BaseDao<Company> {
    /**
     * Get a company by its id.
     * @param pId the company's Id
     * @return a Company
     */
    Company getById(long pId);

    /**
     * Get all Companies.
     * @return a List of all Companies
     */
    List<Company> getAll();

    /**
     * Delete a Company.
     * @param pId the Id of the Company
     */
    void delete(long pId);
}