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
		System.out.println("Voulez-vous : ");
		System.out.println("1 : Gérer les ordinateurs");
		System.out.println("2 : Gérer les compagnies");
		System.out.println("3 : Quitter");
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
							
							System.out.println("Options :");
							System.out.println("1 - Page Précédente");
							System.out.println("2 - Page Suivante");
							System.out.println("3 - Aller à la page");
							System.out.println("4 - Quitter");
							System.out.println("Page "+pageComputer.getPage()+" / "+pageComputer.getNbPages());
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
							
							System.out.println("Options :");
							System.out.println("1 - Page Précédente");
							System.out.println("2 - Page Suivante");
							System.out.println("3 - Aller à la page");
							System.out.println("4 - Quitter");
							System.out.println("Page "+pageCompany.getPage()+" / "+pageCompany.getNbPages());
							
							while (!scanner.hasNextInt()) scanner.next();
							int nextOption = scanner.nextInt();
							
							if(nextOption == 1){
								pageCompany.prevPage();
							}
							else if(nextOption == 2){
								pageCompany.nextPage();						
							}
							else if(nextOption == 3){
								scanner.nextLine();
								System.out.print("Entrez le numéro de la page :");
								String page = "";
								while(page.isEmpty()){
									page = scanner.nextLine();
								}
								pageCompany.setPage(Integer.parseInt(page));
							}
							else if (nextOption == 4){
								continueLoop = false;
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
