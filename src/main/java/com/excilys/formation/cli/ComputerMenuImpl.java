package com.excilys.formation.cli;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
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
    private Page<Computer> pageComputer;

    public ComputerMenuImpl() {
        computerService = new ComputerServiceImpl();
    }

    /**
     * Shows the main operations available
     */
    @Override
    public void startMenu() {
        System.out.println("Voici les opérations disponibles : \n1 : Voir la liste des ordinateurs\n2 : Voir les informations d'un ordinateur\n3 : Créer un ordinateur\n4 : Mettre à jour un ordinateur\n5 : Supprimer un ordinateur\n6 : Retour");
        while (true) {
            while(!MainMenu.scanner.hasNextInt()) {
                MainMenu.scanner.next();
            }
            int choice = MainMenu.scanner.nextInt();
            switch(choice) {
                case 1:
                    listMenu();
                    break;
                case 2:
                    new MainMenu().startMenu();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Shows the list operations available
     */
    @Override
    public void listMenu() {
        boolean continueLoop = true;
        pageComputer = new Page<>(10);
        
        while (continueLoop) {
            showPage();
            continueLoop = manageNavigation();
        }
        
        startMenu();
    }
    
    public void showPage(){
        computerService.getPage(pageComputer);
        StringBuilder stringBuilder = new StringBuilder();
        for (Computer computer : pageComputer.elems) {
            stringBuilder.append(computer.toString()).append("\n");
        }
        stringBuilder.append("Page : ")
        .append(pageComputer.page)
        .append(" / ")
        .append(pageComputer.nbPages)
        .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
    }
    
    public boolean manageNavigation(){
        boolean ok = false;
        while (!ok) {
            while (!MainMenu.scanner.hasNextInt())
                MainMenu.scanner.next();
            int nextOption = MainMenu.scanner.nextInt();

            if (nextOption == 1) {
                ok = pageComputer.prevPage();
                if(!ok) {
                    System.out.println("Vous êtes déjà sur la première page");
                }
            } 
            else if (nextOption == 2) {
                ok = pageComputer.nextPage();
                if(!ok) {
                    System.out.println("Vous êtes déjà sur la dernière page");
                }
            } 
            else if (nextOption == 3) {
                MainMenu.scanner.nextLine();
                System.out.print("Entrez le numéro de la page :");
                String page = "";
                while (page.isEmpty()) {
                    page = MainMenu.scanner.nextLine();
                }
                ok = pageComputer.setPage(Integer.parseInt(page));
                if(!ok) {
                    System.out.println("Cette page n'existe pas");
                }
            } else if (nextOption == 4) {
                return false;
            }
        }
        return true;
    }

    /**
     * Asks the user for a computer id to show him the computer's infos
     */
    @Override
    public void infoMenu() {
        System.out.println("Entrez l'id de l'ordinateur dont vous souhaitez voir les infos (ou entrée pour annuler) :");
        Scanner scan = MainMenu.getScanner();
        scan.nextLine();
        while (!scan.hasNextLine())
            scan.next();
        String infoId = scan.nextLine();
        int idToShow = -1;
        if (MainMenu.isNumeric(infoId)) {
            idToShow = Integer.parseInt(infoId);
        }
        if (idToShow >= 1) {
            Computer computerToShow = computerService.getById(idToShow);
            if (computerToShow != null) {
                System.out.println(new StringBuilder().append("Nom : ")
                        .append(computerToShow.getName())
                        .append("\nDate de début de production : ")
                        .append(computerToShow.getIntroduced())
                        .append("\nDate de fin de production : ")
                        .append(computerToShow.getDiscontinued())
                        .append("\nId de la compagnie : ")
                        .append(computerToShow.getCompany().getId()).toString());
            } else {
                System.out.println("Aucun ordinateur trouvé");
            }
        }

    }

    /**
     * Asks the users the informations about a new computer and create it in the
     * db
     */
    @Override
    public void createMenu() {
        System.out.println("Veuillez entrez un nom pour l'ordinateur :");
        Scanner scan = MainMenu.getScanner();
        String name = "";
        boolean valid = false;
        while (!valid) {
            name = scan.nextLine();
            if (!name.isEmpty())
                valid = true;
        }
        

        System.out.println("Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateIntroduced = null;
        valid = false;
        while (dateIntroduced == null && !valid) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                try {
                    dateIntroduced = LocalDate.parse(line, format);
                } catch (ParseException e) {
                    System.out.println("La date n'a pas un format valide, réessayez");
                }
            } else {
                valid = true;
            }
        }
        

        System.out.println("Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        LocalDate dateDiscontinued = null;
        valid = false;
        while (dateDiscontinued == null && !valid) {
            String line = scan.nextLine();
            if (!line.isEmpty()) {
                try {
                    dateDiscontinued = LocalDate.parse(line, format);
                } catch (ParseException e) {
                    System.out.println("La date n'a pas un format valide, réessayez");
                }
            } else {
                valid = true;
            }
        }

        System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur");
        while (!scan.hasNextInt())
            scan.next();
        int companyId = scan.nextInt();
        
        Computer computer = new Computer.ComputerBuilder(name, ).setDateIntro(dateIntroduced)
                .setDateDisc(dateDiscontinued).build();
        computerService.create(computer);
    }

    /**
     * Asks the user for a computer id then informations to update it
     */
    @Override
    public void updateMenu() {
        System.out.println("Entrez l'id de l'ordinateur à mettre à jour (ou entrée pour annuler) :");
        Scanner scanner = MainMenu.getScanner();
        scanner.nextLine();
        while (!scanner.hasNextLine())
            scanner.next();
        String input = scanner.nextLine();
        int idToUpdate = -1;
        if (MainMenu.isNumeric(input)) {
            idToUpdate = Integer.parseInt(input);
        }

        if (idToUpdate >= 1) {
            Computer computerToUpdate = computerService.getById(idToUpdate);
            if (computerToUpdate != null) {
                System.out.println(
                        "Entrez un nouveau nom si vous souhaitez le changer (" + computerToUpdate.getName() + ") :");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    computerToUpdate.setName(newName);
                }

                System.out.println("Entrez une nouvelle date de début de production ("
                        + computerToUpdate.getIntroduced() + ") au format aaaa-mm-jj (\"null\" pour retirer la date):");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                boolean valid = false;
                while (date == null && !valid) {
                    String line = scanner.nextLine();
                    if (!line.isEmpty() && !line.equals("null")) {
                        try {
                            date = new Date(format.parse(line).getTime());
                            computerToUpdate.setIntroduced(date);
                        } catch (ParseException e) {
                            System.out.println("La date n'a pas un format valide, réessayez");
                        }
                    } else if (line.equals("null")) {
                        computerToUpdate.setIntroduced(null);
                        valid = true;
                    } else {
                        valid = true;
                    }
                }

                System.out
                        .println("Entrez une nouvelle date de fin de production (" + computerToUpdate.getDiscontinued()
                                + ") au format aaaa-mm-jj (\"null\" pour retirer la date):");
                date = null;
                valid = false;
                while (date == null && !valid) {
                    String line = scanner.nextLine();
                    if (!line.isEmpty() && !line.equals("null")) {
                        try {
                            date = new Date(format.parse(line).getTime());
                            computerToUpdate.setDiscontinued(date);
                        } catch (ParseException e) {
                            System.out.println("La date n'a pas un format valide, réessayez");
                        }
                    } else if (line.equals("null")) {
                        computerToUpdate.setDiscontinued(null);
                        valid = true;
                    } else {
                        valid = true;
                    }
                }

                System.out.println(
                        "Vous pouvez entre un nouvel id de compagnie (" + computerToUpdate.getCompanyId() + ") :");
                String newCompanyId = scanner.nextLine();
                if (!newCompanyId.isEmpty()) {
                    computerToUpdate.setCompanyId(Integer.parseInt(newCompanyId));
                }

                computerService.update(computerToUpdate);
            } else {
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
        while (!scanner.hasNextLine())
            scanner.next();
        String input = scanner.nextLine();
        int idToDelete = -1;
        if (MainMenu.isNumeric(input)) {
            idToDelete = Integer.parseInt(input);
        }

        if (idToDelete >= 1) {
            computerService.delete(idToDelete);
        }

    }
}
