package com.excilys.formation.cd.cli;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Scanner;

import com.excilys.formation.cd.dao.CompanyDAO;
import com.excilys.formation.cd.dao.ComputerDAO;
import com.excilys.formation.cd.entities.Company;
import com.excilys.formation.cd.entities.Computer;
import com.excilys.formation.cd.pages.Page;

/**
 * Manages menus and allows users to make operations on the entities
 * @author kfuster
 *
 */
public class MainMenu {
	private IMenu menu;
	private static Scanner scanner;
	private int typeMenu;
	
	public MainMenu(){
		scanner = new Scanner(System.in);
	}
	
	public static Scanner getScanner(){
		return scanner;
	}
	
	/**
	 * Allows the user to chose to manage computers or companies
	 */
	public void startMenu(){
		System.out.println("Voulez-vous :\n"
				+ "1 : Gérer les ordinateurs\n"
				+ "2 : Gérer les compagnies\n"
				+ "3 : Quitter");
		scanner = new Scanner(System.in);
		while(true){
			while (!scanner.hasNextInt()) scanner.next();
			int choice = scanner.nextInt();
			
			switch(choice){
				case 1:
					menu = new ComputerMenu();
					typeMenu = 1;
					
					break;
				case 2:
					menu = new CompanyMenu();
					typeMenu = 2;
					break;
				case 3:
					System.exit(0);
					break;
				default:
					break;
			}
			
			menu.mainMenu();
			choiceMain();
		}
		
		
	}
	
	/**
	 * Manages the choices on the main menus
	 */
	public void choiceMain(){
		while(true){
			while (!scanner.hasNextInt()) scanner.next();
			int choice = scanner.nextInt();
			switch(choice){
				case 1:
					menu.listMenu();
					choiceList();
					break;
				case 2:
					if(typeMenu == 1){
						menu.infoMenu();
						menu.mainMenu();
						choiceMain();
					}
					else{
						this.startMenu();
					}
					break;
				case 3:
					if(typeMenu == 1)
						menu.createMenu();
					menu.mainMenu();
					choiceMain();
					break;
				case 4:
					if(typeMenu == 1)
						menu.updateMenu();
					menu.mainMenu();
					choiceMain();
					break;
				case 5:
					if(typeMenu == 1){
						menu.deleteMenu();
						menu.mainMenu();
						choiceMain();
					}
					break;
				case 6:
					this.startMenu();
					break;
				default:
					break;
			}
		}
	}
	

	/**
	 * Manages the choices on the list menus
	 * and the pagination
	 */
	public void choiceList(){
		while(true){
			while (!scanner.hasNextInt()) scanner.next();
			int choice = scanner.nextInt();
			
			switch(choice){
				case 1:
					boolean continueLoop = true;
					if(typeMenu == 1){
						Page<Computer> pageComputer = new Page<>(10);
						pageComputer.setDao(ComputerDAO.getInstance());
						while(continueLoop){
							
							for(Computer computer : pageComputer.getElems()){
								System.out.println(computer.toString());
							}
							
							System.out.println("Options :\n"
									+ "1 - Page Précédente\n"
									+ "2 - Page Suivante\n"
									+ "3 - Aller à la page\n"
									+ "4 - Quitter\n"
									+ "Page "+pageComputer.getPage()+" / "+pageComputer.getNbPages());
							boolean ok = false;
							while(!ok){
								while (!scanner.hasNextInt()) scanner.next();
								int nextOption = scanner.nextInt();
							
								if(nextOption == 1){
									ok = pageComputer.prevPage();
								}
								else if(nextOption == 2){
									ok = pageComputer.nextPage();						
								}
								else if(nextOption == 3){
									scanner.nextLine();
									System.out.print("Entrez le numéro de la page :");
									String page = "";
									while(page.isEmpty()){
										page = scanner.nextLine();
									}
									ok = pageComputer.setPage(Integer.parseInt(page));
								}
								else if (nextOption == 4){
									ok = true;
									continueLoop = false;
								}
							}
						}
						menu.listMenu();
						choiceList();
					}
					else if (typeMenu == 2){
						Page<Company> pageCompany = new Page<>(10);
						pageCompany.setDao(CompanyDAO.getInstance());
						while(continueLoop){
							
							for(Company company : pageCompany.getElems()){
								System.out.println(company.toString());
							}
							
							System.out.println("Options :\n"
									+ "1 - Page Précédente\n"
									+ "2 - Page Suivante\n"
									+ "3 - Aller à la page\n"
									+ "4 - Quitter\n"
									+ "Page "+pageCompany.getPage()+" / "+pageCompany.getNbPages());
							
							boolean ok = false;
							while(!ok){
								while (!scanner.hasNextInt()) scanner.next();
								int nextOption = scanner.nextInt();
							
								if(nextOption == 1){
									ok = pageCompany.prevPage();
								}
								else if(nextOption == 2){
									ok = pageCompany.nextPage();						
								}
								else if(nextOption == 3){
									scanner.nextLine();
									System.out.print("Entrez le numéro de la page :");
									String page = "";
									while(page.isEmpty()){
										page = scanner.nextLine();
									}
									ok = pageCompany.setPage(Integer.parseInt(page));
								}
								else if (nextOption == 4){
									ok = true;
									continueLoop = false;
								}
							}
						}
						menu.listMenu();
						choiceList();
					}
					
					break;
				case 2:
					if(typeMenu == 2){
						menu.mainMenu();
						choiceMain();
					}
					break;
				case 3:
					if(typeMenu == 1){
						menu.mainMenu();
						choiceMain();
					}
					break;
				default:
					break;
			}
		}
	}
		

	/**
	 * Checks if a string is numeric
	 * @param pStr the string to check
	 * @return
	 */
	public static boolean isNumeric(String pStr)
	{
		if(!pStr.isEmpty()){
			NumberFormat formatter = NumberFormat.getInstance();
			ParsePosition pos = new ParsePosition(0);
			formatter.parse(pStr, pos);
	  		return pStr.length() == pos.getIndex();
		}
		return false;
	}
}
