package com.excilys.formation.cli;

import java.util.Scanner;
import com.excilys.formation.cli.implementation.CompanyMenuImpl;
import com.excilys.formation.cli.implementation.ComputerMenuImpl;
import com.excilys.formation.util.MenuUtil;

/**
 * First menu, allows the user to choose which kind of entity he wants to
 * manage.
 * @author kfuster
 *
 */
public class MainMenu implements Menu {
    public static Scanner scanner;

    /**
     * MainMenu constructor. Initialize scanner.
     */
    public MainMenu() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
    }

    /**
     * Allows the user to chose to manage computers or companies.
     */
    public void startMenu() {
        System.out.println("Voulez-vous :\n1 : Gérer les ordinateurs\n2 : Gérer les compagnies\n3 : Quitter");
        int choice = MenuUtil.waitForInt();
        boolean quit = false;
        switch (choice) {
        case 1:
            ComputerMenuImpl.getInstance().startMenu();
            quit = false;
            break;
        case 2:
            CompanyMenuImpl.getInstance().startMenu();
            quit = false;
            break;
        case 3:
            quit = true;
            break;
        default:
            startMenu();
            quit = false;
            break;
        }
        if (!quit) {
            startMenu();
        }
    }
}