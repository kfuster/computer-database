package com.excilys.formation.persistence;

import java.util.List;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;

public interface CompanyDao extends BaseDao<Company> {
    /**
     * Get a company by its id.
     * @param pId the company's Id
     * @return a Company
     * @throws PersistenceException in case of problems while getting the company
     */
    Company getById(int pId) throws PersistenceException;
    List<Company> getAll() throws PersistenceException;
    /**
     * Deletes a company and all related computers. 
     * @param pID the id of the company to delete
     * @return a boolean indicating if at least a row was affected
     * @throws PersistenceException 
     */
    boolean delete(int pId) throws PersistenceException;
}