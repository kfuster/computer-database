package com.excilys.formation.cli;

public interface CompanyMenu extends Menu {
    /**
     * Initiate the necessary variables and calls the methods to show the list
     * and the operations.
     */
    void list();

    /**
     * Asks the user for the id of a company to delete and delete it and the
     * associated computers.
     */
    void delete();
}