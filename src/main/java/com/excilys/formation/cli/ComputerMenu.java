package com.excilys.formation.cli;

public interface ComputerMenu extends Menu {

    /**
     * Initiate the necessary variables
     * and calls the methods to show the list
     * and the operations
     */
    void list();
    
    /**
     * Asks the user for a computer id to show him the computer's infos
     */
    void info();
    
    /**
     * Asks the users the informations about a new computer and create it in the
     * db
     */
    void create();
    
    /**
     * Asks the user for a computer id then informations to update it
     */
    void update();
    
    /**
     * Asks the user for the id of a computer to delete and delete it
     */
    void delete();

}
