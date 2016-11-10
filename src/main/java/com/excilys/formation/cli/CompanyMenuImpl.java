package com.excilys.formation.cli;

import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.CompanyServiceImpl;

/**
 * Manages the menus and operations for companies
 * 
 * @author kfuster
 *
 */
public class CompanyMenuImpl implements CompanyMenu {
    private CompanyService companyService = new CompanyServiceImpl();
    private Page<Company> pageCompany;
    
    @Override
    public void startMenu() {
        System.out.println("Voici les opérations disponibles :\n1 : Voir la liste des compagnies\n2 : Retour");
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

    @Override
    public void listMenu() {
       boolean continueLoop = true;
       pageCompany = new Page<>(10);
       
       while (continueLoop) {
           showPage();
           continueLoop = manageNavigation();
       }
       
       startMenu();
    }
    
    public void showPage(){
        companyService.getPage(pageCompany);
        StringBuilder stringBuilder = new StringBuilder();
        for (Company company : pageCompany.elems) {
            stringBuilder.append(company.toString()).append("\n");
        }
        stringBuilder.append("Page : ")
        .append(pageCompany.page)
        .append(" / ")
        .append(pageCompany.nbPages)
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
                ok = pageCompany.prevPage();
                if(!ok) {
                    System.out.println("Vous êtes déjà sur la première page");
                }
            } 
            else if (nextOption == 2) {
                ok = pageCompany.nextPage();
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
                ok = pageCompany.setPage(Integer.parseInt(page));
                if(!ok) {
                    System.out.println("Cette page n'existe pas");
                }
            } else if (nextOption == 4) {
                return false;
            }
        }
        return true;
    }
}
