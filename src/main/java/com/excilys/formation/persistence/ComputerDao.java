package com.excilys.formation.persistence;

import java.util.List;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;

public interface ComputerDao extends BaseDao<Computer> {
    /**
     * Get a computer by its name.
     * @param pName the name of the computer to get
     * @return a computer or null
     * @throws PersistenceException in case of problems while getting the
     *             computer
     */
    Computer getByName(String pName) throws PersistenceException;

    /**
     * Get a computer by its id.
     * @param pId the id of the computer to get
     * @return a Computer or null
     * @throws PersistenceException in case of problems while getting the
     *             computer
     */
    Computer getById(long pId) throws PersistenceException;

    /**
     * Deletes all computers from a company.
     * @param id of the company for which we want to delete computer
     * @throws PersistenceException in case of problems while deleting the
     *             computers
     */
    void deleteByCompany(long id) throws PersistenceException;

    /**
     * Deletes a list of Computers.
     * @param idList the computers id in a String of the form "1,2,3,4"
     * @throws PersistenceException in case of problems while deleting the
     *             computers
     */
    void deleteList(List<Long> idList) throws PersistenceException;
}