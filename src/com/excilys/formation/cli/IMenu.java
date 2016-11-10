package com.excilys.formation.cd.cli;

/**
 * Interface for the menus
 * @author kfuster
 *
 */
public interface IMenu {
	/**
	 * Shows the main menu
	 */
	public void mainMenu();
	
	/**
	 * Shows the menu to get lists of elements
	 */
	public void listMenu();
	
	/**
	 * Shows the menu to get info on an element
	 */
	public void infoMenu();
	
	/**
	 * Shows the menu to create an element
	 */
	public void createMenu();
	
	/**
	 * Shows the menu to update an element
	 */
	public void updateMenu();
	
	/**
	 * Shows the menu to delete an element
	 */
	public void deleteMenu();

}
