package com.excilys.formation.persistence;

import java.sql.Connection;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;

public interface ComputerDao extends BaseDao<Computer> {
    /**
     * Get a computer by its name.
     * @param pName the name of the computer to get
     * @return a computer or null
     * @throws PersistenceException in case of problems while getting the computer
     */
    Computer getByName(Connection pConnection, String pName) throws PersistenceException;
    /**
     * Get a computer by its id.
     * @param pId the id of the computer to get
     * @return a Computer or null
     * @throws PersistenceException in case of problems while getting the computer
     */
    Computer getById(Connection pConnection, long pId) throws PersistenceException;
    /**
     * Deletes all computers from a company.
     * @param id of the company for which we want to delete computer
     * @param connection the connection to use
     * @return 
     * @throws PersistenceException in case of problems while deleting the computers
     */
    boolean deleteByCompany(Connection pConnection, long id) throws PersistenceException;
    void deleteList(Connection pConnection, String pIdList) throws PersistenceException;
}