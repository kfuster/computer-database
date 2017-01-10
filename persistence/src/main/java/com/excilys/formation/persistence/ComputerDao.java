package com.excilys.formation.persistence;

import com.excilys.formation.model.Computer;

import java.util.List;

/**
 * Interface representing the DAO of a Computer.
 */
public interface ComputerDao extends BaseDao<Computer> {

    /**
     * Get a computer by its name.
     * @param name the name of the computer to get
     * @return a computer or null
     */
    Computer getByName(String name);

    /**
     * Get a computer by its id.
     * @param id the id of the computer to get
     * @return a Computer or null
     */
    Computer getById(long id);

    /**
     * Deletes all computers from a company.
     * @param id of the company for which we want to delete computer
     */
    void deleteByCompany(long id);

    /**
     * Deletes a list of Computers.
     * @param idList List<Long> representing the ids to delete.
     */
    void deleteList(List<Long> idList);
}