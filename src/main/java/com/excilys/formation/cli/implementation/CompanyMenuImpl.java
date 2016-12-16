package com.excilys.formation.cli.implementation;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.excilys.formation.cli.CompanyMenu;
import com.excilys.formation.cli.Controller;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.util.MenuUtil;

/**
 * Manages the menus and operations for companies.
 * @author kfuster
 *
 */
@Component
public class CompanyMenuImpl implements CompanyMenu {
    private Page<CompanyDto> pageCompany;
    private PageFilter pageFilter;
    private Scanner scanner = MainMenu.scanner;
    @Autowired
    private Controller controller;

    /**
     * CompanyMenuImpl constructor. Initialize CompanyService.
     */
    public CompanyMenuImpl() {
        pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
    }

    public void setController(Controller pController) {
        controller = pController;
    }

    @Override
    public void startMenu() {
        System.out.println(
                "Voici les opérations disponibles :\n1 : Voir la liste des compagnies\n2 : Supprimer une compagnie\n3 : Retour");
        int choice = MenuUtil.waitForInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        switch (choice) {
        case 1:
            list();
            break;
        case 2:
            delete();
            break;
        case 3:
            break;
        default:
            startMenu();
            break;
        }
    }

    @Override
    public void list() {
        pageCompany = new Page<>(10);
        pageFilter.setPageNum(1);
        do {
            pageCompany = controller.getPageCompany(pageFilter);
            showPage();
        } while (MenuUtil.manageNavigation(pageFilter));
        startMenu();
    }

    /**
     * Asks the service to populate the list of elements and show them.
     */
    private void showPage() {
        StringBuilder stringBuilder = new StringBuilder();
        pageCompany.getElements().forEach(company -> stringBuilder.append(company.toString()).append("\n"));
        stringBuilder.append("Page : ").append(pageCompany.getPage()).append(" / ").append(pageCompany.getTotalPages())
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void delete() {
        System.out.println("Entrez l'id de la company à supprimer (ou entrée pour annuler) : ");
        String input = MenuUtil.waitForLine();
        long idToDelete = -1;
        try {
            idToDelete = Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("Vous devez entrer un nombre");
            return;
        }
        controller.deleteCompany(idToDelete);
        System.out.println("Company supprimée");
    }
}
