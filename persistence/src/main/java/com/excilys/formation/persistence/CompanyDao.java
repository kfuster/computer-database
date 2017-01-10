package com.excilys.formation.persistence;

import com.excilys.formation.model.Company;

import java.util.List;

/**
 * Interface representing the DAO of a Company.
 */
public interface CompanyDao extends BaseDao<Company> {

    /**
     * Get a company by its id.
     * @param id the company's Id
     * @return a Company
     */
    Company getById(long id);

    /**
     * Get all Companies.
     * @return a List of all Companies
     */
    List<Company> getAll();

    /**
     * Delete a Company.
     * @param id the Id of the Company
     */
    void delete(long id);
}