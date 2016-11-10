package com.excilys.formation.cli;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Scanner;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;

/**
 * Manages menus and allows users to make operations on the entities
 * 
 * @author kfuster
 *
 */
public class MainMenu implements Menu{
    public static Scanner scanner;

    public MainMenu() {
        scanner = new Scanner(System.in);
    }

    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * Allows the user to chose to manage computers or companies
     */
    public void startMenu() {
        System.out.println("Voulez-vous :\n1 : Gérer les ordinateurs\n2 : Gérer les compagnies\n3 : Quitter");
        scanner = new Scanner(System.in);
        while (true) {
            while (!scanner.hasNextInt())
                scanner.next();
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                new ComputerMenuImpl().startMenu();
                break;
            case 2:
                new CompanyMenuImpl().startMenu();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
            }
        }

    }

    /**
     * Manages the choices on the list menus and the pagination
     */
    public void choiceList() {
        while (true) {
            while (!scanner.hasNextInt())
                scanner.next();
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                boolean continueLoop = true;
                if (typeMenu == 1) {
                    Page<Computer> pageComputer = new Page<>(10);
                    while (continueLoop) {

                        for (Computer computer : pageComputer.elems) {
                            System.out.println(computer.toString());
                        }

                        System.out.println(new StringBuilder().append("Options :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter\nPage ")
                                .append(pageComputer.page)
                                .append(" / ")
                                .append(pageComputer.page).toString());
                        boolean ok = false;
                        while (!ok) {
                            while (!scanner.hasNextInt())
                                scanner.next();
                            int nextOption = scanner.nextInt();

                            if (nextOption == 1) {
                                ok = pageComputer.prevPage();
                            } else if (nextOption == 2) {
                                ok = pageComputer.nextPage();
                            } else if (nextOption == 3) {
                                scanner.nextLine();
                                System.out.print("Entrez le numéro de la page :");
                                String page = "";
                                while (page.isEmpty()) {
                                    page = scanner.nextLine();
                                }
                                ok = pageComputer.setPage(Integer.parseInt(page));
                            } else if (nextOption == 4) {
                                ok = true;
                                continueLoop = false;
                            }
                        }
                    }
                    menu.listMenu();
                    choiceList();
                } else if (typeMenu == 2) {
                    Page<Company> pageCompany = new Page<>(10);
                    while (continueLoop) {

                        for (Company company : pageCompany.elems) {
                            System.out.println(company.toString());
                        }

                        System.out.println(new StringBuilder().append("Options :\n1 - Page Précédente\n2 - Page Suivante\n3 - Aller à la page\n4 - Quitter\nPage ")
                                .append(pageCompany.page)
                                .append(" / ")
                                .append(pageCompany.page).toString());

                        boolean ok = false;
                        while (!ok) {
                            while (!scanner.hasNextInt())
                                scanner.next();
                            int nextOption = scanner.nextInt();

                            if (nextOption == 1) {
                                ok = pageCompany.prevPage();
                            } else if (nextOption == 2) {
                                ok = pageCompany.nextPage();
                            } else if (nextOption == 3) {
                                scanner.nextLine();
                                System.out.print("Entrez le numéro de la page :");
                                String page = "";
                                while (page.isEmpty()) {
                                    page = scanner.nextLine();
                                }
                                ok = pageCompany.setPage(Integer.parseInt(page));
                            } else if (nextOption == 4) {
                                ok = true;
                                continueLoop = false;
                            }
                        }
                    }
                    menu.listMenu();
                    choiceList();
                }

                break;
            case 2:
                if (typeMenu == 2) {
                    menu.startMenu();
                    choiceMain();
                }
                break;
            case 3:
                if (typeMenu == 1) {
                    menu.startMenu();
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
     * 
     * @param pStr the string to check
     * @return
     */
    public static boolean isNumeric(String pStr) {
        if (!pStr.isEmpty()) {
            NumberFormat formatter = NumberFormat.getInstance();
            ParsePosition pos = new ParsePosition(0);
            formatter.parse(pStr, pos);
            return pStr.length() == pos.getIndex();
        }
        return false;
    }
}
