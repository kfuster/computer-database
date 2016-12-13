package com.excilys.formation.cli;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import com.excilys.formation.util.MenuUtil;

/**
 * First menu, allows the user to choose which kind of entity he wants to
 * manage.
 * @author kfuster
 *
 */
public class MainMenu implements Menu {
    public static Scanner scanner = new Scanner(System.in);
    @Autowired
    private static ComputerMenu computerMenu;
    @Autowired
    private static CompanyMenu companyMenu;
    
    /**
     * MainMenu constructor. Initialize scanner.
     */
    public MainMenu() {
    }
    
    public void setComputerMenu(ComputerMenu pComputerMenu) {
        computerMenu = pComputerMenu;
    }

    public void setCompanyMenu(CompanyMenu pCompanyMenu) {
        companyMenu = pCompanyMenu;
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
            computerMenu.startMenu();
            quit = false;
            break;
        case 2:
            companyMenu.startMenu();
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