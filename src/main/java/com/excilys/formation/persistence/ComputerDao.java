package com.excilys.formation.persistence;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;

public interface ComputerDao extends BaseDao<Computer> {
    /**
     * Get a computer by its name.
     * @param pName the name of the computer to get
     * @return a computer or null
     * @throws PersistenceException in case of problems while getting the computer
     */
    Computer getByName(String pName) throws PersistenceException;
    /**
     * Get a computer by its id.
     * @param pId the id of the computer to get
     * @return a Computer or null
     * @throws PersistenceException in case of problems while getting the computer
     */
    Computer getById(int pId) throws PersistenceException;
}