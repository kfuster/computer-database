package com.excilys.formation.cli;

import com.excilys.formation.cli.util.MenuUtil;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.CompanyServiceImpl;

/**
 * Manages the menus and operations for companies.
 * @author kfuster
 *
 */
public class CompanyMenuImpl implements CompanyMenu {
    private static CompanyService companyService = new CompanyServiceImpl();
    private Page<CompanyDto> pageCompany;
    /**
     * CompanyMenuImpl constructor.
     * Initialize CompanyService.
     */
    public CompanyMenuImpl() {
        companyService = new CompanyServiceImpl();
    }
    @Override
    public void startMenu() {
        System.out.println("Voici les opérations disponibles :\n1 : Voir la liste des compagnies\n2 : Retour");
        while (true) {
            int choice = MenuUtil.waitForInt();
            switch (choice) {
                case 1:
                    list();
                    break;
                case 2:
                    new MainMenu().startMenu();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void list() {
        boolean continueLoop = true;
        pageCompany = new Page<>(10);

        while (continueLoop) {
            showPage();
            continueLoop = MenuUtil.manageNavigation(pageCompany);
        }
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
        System.out.println(stringBuilder.toString());
    }
}
