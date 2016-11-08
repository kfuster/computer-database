package com.excilys.formation.cd.cli;

/**
 * Manages the menus and operations for companies
 * @author kfuster
 *
 */
public class CompanyMenu implements IMenu{

	/**
	 * Shows the main operations available
	 */
	@Override
	public void mainMenu() {
		System.out.println("Voici les opérations disponibles : ");
		System.out.println("1 : Voir la liste des compagnies");
		System.out.println("2 : Retour");
	}

	/**
	 * Shows the list operations available
	 */
	@Override
	public void listMenu() {
		System.out.println("1 : Lister toutes les compagnies");
		System.out.println("2 : Retour");
	}

	@Override
	public void infoMenu() {
		// TODO Auto-generated method stub
	}

	@Override
	public void createMenu() {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateMenu() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteMenu() {
		// TODO Auto-generated method stub	
	}

}
