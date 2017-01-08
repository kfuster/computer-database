package com.excilys.formation.cli.implementation;

import com.excilys.formation.cli.ComputerMenu;
import com.excilys.formation.cli.Controller;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Manage the menus and operations for the computers.
 * @author kfuster
 *
 */
@Component
public class ComputerMenuImpl implements ComputerMenu {
    private Page<ComputerDto> pageComputer;
    private Scanner scanner = MainMenu.scanner;
    private PageFilter pageFilter;
    @Autowired
    private Controller controller;

    /**
     * ComputerMenuImpl constructor. Initialize ComputerService.
     */
    public ComputerMenuImpl() {
        pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
    }

    /**
     * Shows the main operations available.
     * <ul>
     * <li>1 : Computer list</li>
     * <li>2 : Computer informations</li>
     * <li>3 : Create</li>
     * <li>4 : Update</li>
     * <li>5 : Delete</li>
     * <li>6 : Back</li>
     * </ul>
     */
    @Override
    public void startMenu() {
        System.out.println(
                "Voici les opérations disponibles : \n1 : Voir la liste des ordinateurs\n2 : Voir les informations d'un ordinateur\n3 : Créer un ordinateur\n4 : Mettre à jour un ordinateur\n5 : Supprimer un ordinateur\n6 : Retour");
        int choice = MenuUtil.waitForInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        boolean quit;
        switch (choice) {
        case 1:
            list();
            quit = false;
            break;
        case 2:
            info();
            quit = false;
            break;
        case 3:
            create();
            quit = false;
            break;
        case 4:
            update();
            quit = false;
            break;
        case 5:
            delete();
            quit = false;
            break;
        case 6:
            quit = true;
            break;
        default:
            System.out.println("Opération non disponible");
            quit = false;
            break;
        }
        if (!quit) {
            startMenu();
        }
    }

    @Override
    public void list() {
        pageFilter.setPageNum(1);
        pageComputer = new Page<>(10);
        // While the user doesn't quit the list, continue.
        do {
            pageComputer = controller.getPageComputer(pageFilter);
            pageFilter.setNbPage(pageComputer.getTotalPages());
            showPage();
        } while (MenuUtil.manageNavigation(pageFilter));
    }

    /**
     * Asks the service to populate the list of elements and show them.
     */
    private void showPage() {
        StringBuilder stringBuilder = new StringBuilder();
        pageComputer.getElements().forEach(computer -> stringBuilder.append(computer.toString()).append("\n"));
        stringBuilder.append("Page : ").append(pageComputer.getPage()).append(" / ").append(pageComputer.getTotalPages())
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void info() {
        System.out.println("Entrez l'id de l'ordinateur dont vous souhaitez voir les infos (ou entrée pour annuler) :");
        String infoId = MenuUtil.waitForLine();
        long idToShow;
        try {
            idToShow = Long.parseLong(infoId);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        ComputerDto computerToShow = controller.getComputerById(idToShow);
        if (computerToShow != null) {
            System.out.println("Nom : " + computerToShow.getName() +
                    "\nDate de début de production : " + computerToShow.getIntroduced() +
                    "\nDate de fin de production : " + computerToShow.getDiscontinued() +
                    "\nId de la compagnie : " + computerToShow.getCompanyId());
        } else {
            System.out.println("Aucun ordinateur trouvé");
        }
    }

    @Override
    public void create() {
        ComputerDto computerDto = new ComputerDto();
        System.out.println("Veuillez entrez un nom pour l'ordinateur (ou entrée pour annuler) :");
        String name;
        name = MenuUtil.waitForLine();
        if (name.isEmpty()) {
            return;
        }
        computerDto.setName(name);
        System.out.println(
                "Vous pouvez entrez une date de début de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setIntroduced(MenuUtil.inputDate());
        System.out.println(
                "Vous pouvez entrez une date d'arrêt de production au format aaaa-mm-jj (ou appuyer sur entrée pour passer)");
        computerDto.setDiscontinued(MenuUtil.inputDate());
        System.out.println("Entrez l'id de la compagnie fabricant l'ordinateur");
        computerDto.setCompanyId((long) MenuUtil.waitForInt());
        controller.createComputer(computerDto);
    }

    @Override
    public void update() {
        System.out.println("Entrez l'id de l'ordinateur à mettre à jour (ou entrée pour annuler) :");
        String input = MenuUtil.waitForLine();
        long idToUpdate;
        try {
            idToUpdate = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        ComputerDto computerDto = controller.getComputerById(idToUpdate);
        if (computerDto != null) {
            // Asking for new name
            System.out.println("Entrez un nouveau nom si vous souhaitez le changer (" +
                    computerDto.getName() + ") :");
            String newName = MenuUtil.waitForLine();
            if (!newName.isEmpty()) {
                computerDto.setName(newName);
            }
            // Asking for new introduced date
            System.out.println("Entrez une nouvelle date de début de production (" +
                    computerDto.getIntroduced() +
                    ") au format aaaa-mm-jj (\"null\" pour retirer la date):");
            computerDto.setIntroduced(MenuUtil.inputNewDate(computerDto.getIntroduced()));
            // Asking for new discontinued date
            System.out.println("Entrez une nouvelle date de fin de production (" +
                    computerDto.getDiscontinued() +
                    ") au format aaaa-mm-jj (\"null\" pour retirer la date):");
            computerDto.setDiscontinued(MenuUtil.inputNewDate(computerDto.getDiscontinued()));
            // Asking for new company id
            System.out.println("Vous pouvez entrer un nouvel id de compagnie (" + computerDto.getCompanyId() + ") :");
            String newCompanyId = scanner.nextLine();
            if (!newCompanyId.isEmpty()) {
                try {
                    long companyId = Long.parseLong(newCompanyId);
                    computerDto.setCompanyId(companyId);
                } catch (NumberFormatException e) {
                    System.out.println("Vous devez entrer un nombre");
                    return;
                }
            }
            controller.updateComputer(computerDto);
        } else {
            System.out.println("Aucun ordinateur trouvé");
        }
    }

    @Override
    public void delete() {
        System.out.println("Entrez l'id de l'ordinateur à supprimer (ou entrée pour annuler) : ");
        String input = MenuUtil.waitForLine();
        long idToDelete;
        try {
            idToDelete = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        controller.deleteComputer(idToDelete);
    }
}