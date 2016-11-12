package com.excilys.formation.cli;

import java.util.Scanner;

import com.excilys.formation.cli.util.MenuUtil;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.ComputerServiceImpl;

/**
 * Manage the menus and operations for the computers
 * 
 * @author kfuster
 *
 */
public class ComputerMenuImpl implements ComputerMenu {
    private ComputerService computerService;
    private Page<ComputerDto> pageComputer;

    public ComputerMenuImpl() {
        computerService = new ComputerServiceImpl();
    }

    /**
     * Shows the main operations available
     * <ul>
     *  <li>
     *      1 : Computer list
     *  </li>
     *  <li>
     *      2 : Computer informations
     *  </li>
     *  <li>
     *      3 : Create
     *  </li>
     *  <li>
     *      4 : Update
     *  </li>
     *  <li>
     *      5 : Delete 
     *  </li>
     *  <li>
     *      6 : Back
     *  </li>
     * </ul>
     */
    @Override
    public void startMenu() {
    	while (true) {
    		System.out.println("Voici les opérations disponibles : \n1 : Voir la liste des ordinateurs\n2 : Voir les informations d'un ordinateur\n3 : Créer un ordinateur\n4 : Mettre à jour un ordinateur\n5 : Supprimer un ordinateur\n6 : Retour");
        
    		int choice = MenuUtil.waitForInt();
            switch(choice) {
                case 1:
                    list();
                    break;
                case 2:
                    info();
                    break;
                case 3:
                	create();
                	break;
                case 4:
                	update();
                	break;
                case 5:
                	delete();
                	break;
                case 6:
                    new MainMenu().startMenu();
                    break;
                default:
                	System.out.println("Opération non disponible");
                    break;
            }
        }
    }


    @Override
    public void list() {
        boolean continueLoop = true;
        pageComputer = new Page<>(10);
        
        /**
         * While the user doesn't quit the list, continue
         */
        while (continueLoop) {
            showPage();
            continueLoop = MenuUtil.manageNavigation(pageComputer);
        }
        startMenu();
    }
    
    /**
     * Asks the service to populate the list of elements
     * and show them
     */
    private void showPage() {
        computerService.getPage(pageComputer);
        StringBuilder stringBuilder = new StringBuilder();
        for (ComputerDto computer : pageComputer.elems) {
            stringBuilder.append(computer.toString()).append("\n");
        }
        stringBuilder.append("Page : ")
        .append(pageComputer.page)
        .append(" / ")
        .append(pageComputer.nbPages)
        .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void info() {
        System.out.println("Entrez l'id de l'ordinateur dont vous souhaitez voir les infos (ou entrée pour annuler) :");
        Scanner scan = MainMenu.scanner;
        scan.nextLine();
        String infoId = MenuUtil.waitForLine();
        int idToShow = -1;
        if (MenuUtil.isInteger(infoId)) {
            idToShow = Integer.parseInt(infoId);
        }
        if (idToShow >= 1) {
            ComputerDto computerToShow = computerService.getById(idToShow);
            if (computerToShow != null) {
                System.out.println(new StringBuilder().append("Nom : ")
                        .append(computerToShow.name)
                        .append("\nDate de début de production : ")
                        .append(computerToShow.introduced)
                        .append("\nDate de fin de production : ")
                        .append(computerToShow.discontinued)
                        .append("\nId de la compagnie : ")
                        .append(computerToShow.companyId).toString());
            } else {
                System.out.println("Aucun ordinateur trouvé");
            }
        }

    }

    @Override
    public void create() {
    	ComputerDto computerDto = new ComputerDto();
        System.out.println("Veuillez entrez un nom pour l'ordinateur :");
        Scanner scanner = MainMenu.scanner;
        String name = "";
        boolean valid = false;
        while (!valid) {
            name = scanner.nextLine();
            if (!name.isEmpty())
                valid = true;
        }     
        computerDto.name = name;

        System.out.println("Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.introduced = MenuUtil.inputDate();
        
        System.out.println("Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.discontinued = MenuUtil.inputDate();
        
        System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur");
        computerDto.companyId = MenuUtil.waitForInt(); 
   
        try {
			computerService.create(computerDto);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void update() {
        System.out.println("Entrez l'id de l'ordinateur à mettre à jour (ou entrée pour annuler) :");
        Scanner scanner = MainMenu.scanner;
        scanner.nextLine();
        String input = MenuUtil.waitForLine();
        int idToUpdate = -1;
        if (MenuUtil.isInteger(input)) {
            idToUpdate = Integer.parseInt(input);
        }

        if (idToUpdate >= 1) {
            ComputerDto computerDto = computerService.getById(idToUpdate); 
            if (computerDto != null) {
                
                //Asking for new name
                System.out.println(new StringBuilder().append("Entrez un nouveau nom si vous souhaitez le changer (")
                        .append(computerDto.name)
                        .append(") :").toString());
                
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    computerDto.name = newName;
                }

                //Asking for new introduced date
                System.out.println(new StringBuilder().append("Entrez une nouvelle date de début de production (")
                		.append(computerDto.introduced)
                		.append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
                computerDto.introduced = MenuUtil.inputNewDate(computerDto.introduced);
                
                
                //Asking for new discontinued date
                System.out.println(new StringBuilder().append("Entrez une nouvelle date de fin de production (")
                		.append(computerDto.discontinued)
                		.append(") au format aaaa-mm-jj (\"null\" pour retirer la date):").toString());
                computerDto.discontinued = MenuUtil.inputNewDate(computerDto.discontinued);
                
                //Asking for new company id
                System.out.println("Vous pouvez entrer un nouvel id de compagnie (" + computerDto.companyId + ") :");
                String newCompanyId = scanner.nextLine();
                if (!newCompanyId.isEmpty() && MenuUtil.isInteger(newCompanyId)) {
                	computerDto.companyId = Integer.parseInt(newCompanyId);
                }

                try {
					computerService.update(computerDto);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
            } else {
                System.out.println("Aucun ordinateur trouvé");
            }
        }
    }

    @Override
    public void delete() {
        System.out.println("Entrez l'id de l'ordinateur à supprimer (ou entrée pour annuler) : ");
        Scanner scanner = MainMenu.scanner;
        scanner.nextLine();
        String input = MenuUtil.waitForLine();
        int idToDelete = -1;
        if (MenuUtil.isInteger(input)) {
            idToDelete = Integer.parseInt(input);
        }
        if (idToDelete >= 1) {
            try {
				computerService.delete(idToDelete);
				System.out.println("Ordinateur supprimé");
			} catch (ServiceException e) {
				e.printStackTrace();
			}
        }
    }
}
