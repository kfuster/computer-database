package com.excilys.formation.cd.cli;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.excilys.formation.cd.dao.ComputerDAO;
import com.excilys.formation.cd.entities.Computer;

/**
 * Manage the menus and operations for the computers
 * @author kfuster
 *
 */
public class ComputerMenu implements IMenu{
	private ComputerDAO computerDAO;
	
	public ComputerMenu(){
		computerDAO = ComputerDAO.getInstance();
	}
	
	/**
	 * Shows the main operations available
	 */
	@Override
	public void mainMenu(){
		System.out.println("Voici les opérations disponibles : \n"
				+ "1 : Voir la liste des ordinateurs\n"
				+ "2 : Voir les informations d'un ordinateur\n"
				+ "3 : Créer un ordinateur\n"
				+ "4 : Mettre à jour un ordinateur\n"
				+ "5 : Supprimer un ordinateur\n"
				+ "6 : Retour");
	}
	
	
	/**
	 * Shows the list operations available
	 */
	@Override
	public void listMenu(){
		System.out.println("1 : Lister tous les ordinateurs\n"
				+ "2 : Lister les ordinateurs d'une company (WIP)\n"
				+ "3 : Retour");
	}
	
	/**
	 * Asks the user for a computer id to show him the computer's infos
	 */
	@Override
	public void infoMenu() {
		System.out.println("Entrez l'id de l'ordinateur dont vous souhaitez voir les infos (ou entrée pour annuler) :");
		Scanner scan = MainMenu.getScanner();
		scan.nextLine();
		while(!scan.hasNextLine()) scan.next();
		String infoId = scan.nextLine();
		int idToShow = -1; 
		if(MainMenu.isNumeric(infoId)){
			idToShow = Integer.parseInt(infoId);
		}
		if(idToShow >= 1){
			computerDAO.openConnection();
			Computer computerToShow = computerDAO.getByID(idToShow);
			if(computerToShow != null){
				System.out.println("Nom : "+computerToShow.getName()+"\n"
						+ "Date de début de production : "+computerToShow.getIntroduced()+"\n"
						+ "Date de fin de production : "+computerToShow.getDiscontinued()+"\n"
						+ "Id de la compagnie : "+computerToShow.getCompanyId());
			}
			else{
				System.out.println("Aucun ordinateur trouvé");
			}
		}
		
	}

	/**
	 * Asks the users the informations about a new computer and create it in the db
	 */
	@Override
	public void createMenu() {
		System.out.println("Veuillez entrez un nom pour l'ordinateur :");
		Scanner scan = MainMenu.getScanner();
		String name = "";
		boolean valid = false;
		while(!valid){
			name = scan.nextLine();
			if(!name.isEmpty())
				valid = true;
		}
		Computer computer = new Computer(name);
		
		System.out.println("Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		valid = false;
		while (date == null && !valid) {
			String line = scan.nextLine();
			if(!line.isEmpty()){
				try {
					date = new Date(format.parse(line).getTime());
				} catch (ParseException e) {
					System.out.println("La date n'a pas un format valide, réessayez");
				}
			}
			else{
				valid = true;
			}
		}
		computer.setIntroduced(date);
		
		System.out.println("Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
		date = null;
		valid = false;
		while (date == null && !valid) {
			String line = scan.nextLine();
			if(!line.isEmpty()){
				try {
					date = new Date(format.parse(line).getTime());
				} catch (ParseException e) {
					System.out.println("La date n'a pas un format valide, réessayez");
				}
			}
			else{
				valid = true;
			}
		}
		computer.setDiscontinued(date);
		
		System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur");
		while (!scan.hasNextInt()) scan.next();
		int companyId = scan.nextInt();
		computer.setCompanyId(companyId);
		scan.nextLine();

		computerDAO.openConnection();
		computerDAO.create(computer);
		computerDAO.closeConnection();
	}
	
	/**
	 * Asks the user for a computer id then informations to update it
	 */
	@Override
	public void updateMenu() {
		System.out.println("Entrez l'id de l'ordinateur à mettre à jour (ou entrée pour annuler) :");
		Scanner scanner = MainMenu.getScanner();
		scanner.nextLine();
		while(!scanner.hasNextLine()) scanner.next();
		String input = scanner.nextLine();
		int idToUpdate = -1; 
		if(MainMenu.isNumeric(input)){
			idToUpdate = Integer.parseInt(input);
		}
		
		if(idToUpdate >= 1){
			computerDAO.openConnection();
			Computer computerToUpdate = computerDAO.getByID(idToUpdate);
			if(computerToUpdate != null){
				System.out.println("Entrez un nouveau nom si vous souhaitez le changer ("+computerToUpdate.getName()+") :");
				String newName = scanner.nextLine();
				if(!newName.isEmpty()){
					computerToUpdate.setName(newName);
				}
				
				System.out.println("Entrez une nouvelle date de début de production ("+computerToUpdate.getIntroduced()+") au format aaaa-mm-jj (\"null\" pour retirer la date):");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				boolean valid = false;
				while (date == null && !valid) {
					String line = scanner.nextLine();
					if(!line.isEmpty() && !line.equals("null")){
						try {
							date = new Date(format.parse(line).getTime());
							computerToUpdate.setIntroduced(date);
						} catch (ParseException e) {
							System.out.println("La date n'a pas un format valide, réessayez");
						}
					}
					else if(line.equals("null")){
						computerToUpdate.setIntroduced(null);
						valid = true;
					}
					else{
						valid = true;
					}
				}
				
				System.out.println("Entrez une nouvelle date de fin de production ("+computerToUpdate.getDiscontinued()+") au format aaaa-mm-jj (\"null\" pour retirer la date):");
				date = null;
				valid = false;
				while (date == null && !valid) {
					String line = scanner.nextLine();
					if(!line.isEmpty() && !line.equals("null")){
						try {
							date = new Date(format.parse(line).getTime());
							computerToUpdate.setDiscontinued(date);
						} catch (ParseException e) {
							System.out.println("La date n'a pas un format valide, réessayez");
						}
					}
					else if(line.equals("null")){
						computerToUpdate.setDiscontinued(null);
						valid = true;
					}
					else{
						valid = true;
					}
				}
				
				System.out.println("Vous pouvez entre un nouvel id de compagnie ("+computerToUpdate.getCompanyId()+") :");
				String newCompanyId = scanner.nextLine();
				if(!newCompanyId.isEmpty()){
					computerToUpdate.setCompanyId(Integer.parseInt(newCompanyId));
				}
				
				computerDAO.update(computerToUpdate);
				computerDAO.closeConnection();
			}
			else{
				System.out.println("Aucun ordinateur trouvé");
			}
		}

	}
	
	/**
	 * Asks the user for the id of a computer to delete and delete it
	 */
	@Override
	public void deleteMenu() {
		System.out.println("Entrez l'id de l'ordinateur à supprimer (ou entrée pour annuler) : ");
		Scanner scanner = MainMenu.getScanner();
		scanner.nextLine();
		while(!scanner.hasNextLine()) scanner.next();
		String input = scanner.nextLine();
		int idToDelete = -1; 
		if(MainMenu.isNumeric(input)){
			idToDelete = Integer.parseInt(input);
		}
		
		if(idToDelete >= 1){
			computerDAO.openConnection();
			computerDAO.delete(idToDelete);
			computerDAO.closeConnection();
		}
			
	}
}
