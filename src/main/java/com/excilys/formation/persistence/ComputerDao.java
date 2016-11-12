package com.excilys.formation.persistence;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;

public interface ComputerDao extends BaseDao<Computer> {
    /**
     * Get a computer by its name
     * @param name the name of the computer to get
     * @return a computer or null
     * @throws PersistenceException
     */
    Computer getByName(String name) throws PersistenceException;
    
    /**
     * Get a computer by its id
     * @param id the id of the computer to get
     * @return a Computer or null
     * @throws PersistenceException 
     */
    Computer getById(int pId) throws PersistenceException;
}