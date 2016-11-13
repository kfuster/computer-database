package com.excilys.formation.cli;

import java.util.Scanner;

/**
 * First menu, allows the user to choose which kind of entity he wants to manage.
 * @author kfuster
 *
 */
public class MainMenu implements Menu {
    public static Scanner scanner;
    /**
     * MainMenu constructor.
     * Initialize scanner.
     */
    public MainMenu() {
        scanner = new Scanner(System.in);
    }
    /**
     * Allows the user to chose to manage computers or companies.
     */
    public void startMenu() {
        System.out.println("Voulez-vous :\n1 : Gérer les ordinateurs\n2 : Gérer les compagnies\n3 : Quitter");
        scanner = new Scanner(System.in);
        while (true) {
            while (!scanner.hasNextInt()) {
                scanner.next();
            }
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
}
