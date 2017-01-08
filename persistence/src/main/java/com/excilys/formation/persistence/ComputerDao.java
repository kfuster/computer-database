package com.excilys.formation.persistence;

import com.excilys.formation.model.Computer;

import java.util.List;

public interface ComputerDao extends BaseDao<Computer> {
    /**
     * Get a computer by its name.
     * @param pName the name of the computer to get
     * @return a computer or null
     */
    Computer getByName(String pName);

    /**
     * Get a computer by its id.
     * @param pId the id of the computer to get
     * @return a Computer or null
     */
    Computer getById(long pId);

    /**
     * Deletes all computers from a company.
     * @param id of the company for which we want to delete computer
     */
    void deleteByCompany(long id);

    /**
     * Deletes a list of Computers.
     * @param idList the computers id in a String of the form "1,2,3,4"
     */
    void deleteList(List<Long> idList);
}