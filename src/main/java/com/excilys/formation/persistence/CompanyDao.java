package com.excilys.formation.persistence;

import java.sql.Connection;
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

    List<Company> getAll() throws PersistenceException;

    void delete(Connection pConnection, long pId) throws PersistenceException;
}