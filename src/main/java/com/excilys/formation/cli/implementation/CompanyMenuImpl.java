package com.excilys.formation.cli.implementation;

import java.util.Scanner;
import org.slf4j.LoggerFactory;
import com.excilys.formation.cli.CompanyMenu;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.implementation.CompanyServiceImpl;
import com.excilys.formation.util.MenuUtil;
import ch.qos.logback.classic.Logger;

/**
 * Manages the menus and operations for companies.
 * @author kfuster
 *
 */
public class CompanyMenuImpl implements CompanyMenu {
    final Logger logger = (Logger) LoggerFactory.getLogger(CompanyMenuImpl.class);
    private static CompanyService companyService;
    private static CompanyMenuImpl companyMenu;
    private Page<CompanyDto> pageCompany;
    private Scanner scanner = MainMenu.scanner;
    /**
     * CompanyMenuImpl constructor.
     * Initialize CompanyService.
     */
    private CompanyMenuImpl() {
        companyService = CompanyServiceImpl.getInstance();
    }
    /**
     * Getter for the CompanyMenuImpl instance.
     * Initializes it if null.
     * @return the instance of CompanyMenuImpl
     */
    public static CompanyMenuImpl getInstance() {
        if (companyMenu == null) {
            companyMenu = new CompanyMenuImpl();
        }
        return companyMenu;
    }
    @Override
    public void startMenu() {
        logger.info("Voici les opérations disponibles :\n1 : Voir la liste des compagnies\n2 : Supprimer une compagnie\n3 : Retour");
        int choice = MenuUtil.waitForInt();
        if(scanner.hasNextLine()) {
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
        do {
            showPage();
        }while(MenuUtil.manageNavigation(pageCompany));
        startMenu();
    }

    /**
     * Asks the service to populate the list of elements and show them.
     */
    private void showPage() {
        companyService.getPage(pageCompany);
        StringBuilder stringBuilder = new StringBuilder();
        for (CompanyDto company : pageCompany.elems) {
            stringBuilder.append(company.toString()).append("\n");
        }
        stringBuilder.append("Page : ").append(pageCompany.page).append(" / ").append(pageCompany.nbPages)
                .append("\nOptions :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter");
        logger.info(stringBuilder.toString());
    }
    @Override
    public void delete() {
        logger.info("Entrez l'id de la company à supprimer (ou entrée pour annuler) : ");
        String input = MenuUtil.waitForLine();
        int idToDelete = -1;
        if (MenuUtil.isInteger(input)) {
            idToDelete = Integer.parseInt(input);
        }
        if (idToDelete >= 1) {
            try {
                companyService.delete(idToDelete);
                logger.info("Company supprimé");
            } catch (ServiceException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
