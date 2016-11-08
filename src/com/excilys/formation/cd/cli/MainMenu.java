package com.excilys.formation.cd.cli;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Scanner;

import com.excilys.formation.cd.dao.CompanyDAO;
import com.excilys.formation.cd.dao.ComputerDAO;
import com.excilys.formation.cd.entities.Company;
import com.excilys.formation.cd.entities.Computer;

/**
 * Manages menus and allows users to make operations on the entities
 * @author kfuster
 *
 */
public class MainMenu {
	private IMenu menu;
	private static Scanner scanner;
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	private int typeMenu;
	
	public MainMenu(){
		scanner = new Scanner(System.in);
		computerDAO = ComputerDAO.getInstance();
		companyDAO = CompanyDAO.getInstance();
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
	 */
	public void choiceList(){
		while(true){
			while (!scanner.hasNextInt()) scanner.next();
			int choice = scanner.nextInt();
			
			switch(choice){
				case 1:
					if(typeMenu == 1){
						computerDAO.openConnection();
						for(Computer computer :computerDAO.getAll()){
							System.out.println(computer.toString());
						}
						computerDAO.closeConnection();
						menu.listMenu();
						choiceList();
					}
					else if (typeMenu == 2){
						companyDAO.openConnection();
						for(Company company : companyDAO.getAll()){
							System.out.println(company.toString());
						}
						companyDAO.closeConnection();
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
