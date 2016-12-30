package com.excilys.formation.persistence;

import java.util.List;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Company;

public interface CompanyDao extends BaseDao<Company> {
    /**
     * Get a company by its id.
     * @param pId the company's Id
     * @return a Company
     * @throws PersistenceException in case of problems while getting the
     *             company
     */
    Company getById(long pId) throws PersistenceException;

    /**
     * Get all Companies.
     * @return a List of all Companies
     * @throws PersistenceException in case of problems while getting all the
     *             companies
     */
    List<Company> getAll() throws PersistenceException;

    /**
     * Delete a Company.
     * @param pId the Id of the Company
     * @throws PersistenceException in case of problems while deleting the
     *             company
     */
    void delete(long pId) throws PersistenceException;
}