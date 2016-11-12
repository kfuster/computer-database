package com.excilys.formation.persistence;

import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;

public interface CompanyDao extends BaseDao<Company> {

    /**
     * Get a company by its id
     * @param pId
     * @return
     */
    Company getById(int pId) throws PersistenceException;
}
