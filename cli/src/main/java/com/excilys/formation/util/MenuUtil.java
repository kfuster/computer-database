package com.excilys.formation.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.model.util.PageFilter;

/**
 * A Util class for menus.
 * @author kfuster
 *
 */
public class MenuUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Wait until the scanner has a new line and returns it.
     * @return the next line from the scanner
     */
    public static String waitForLine() {
        while (!MainMenu.scanner.hasNextLine()) {
            MainMenu.scanner.next();
        }
        return MainMenu.scanner.nextLine();
    }

    /**
     * Wait until the scanner has a new int and returns it.
     * @return the next int from the scanner
     */
    public static int waitForInt() {
        while (!MainMenu.scanner.hasNextInt()) {
            MainMenu.scanner.next();
        }
        return MainMenu.scanner.nextInt();
    }

    /**
     * Gets a new line from the scanner until it's empty, "null" or a valid date
     * and returns the date.
     * @return a LocalDate or null
     */
    public static String inputDate() {
        LocalDate date = null;
        boolean valid = false;
        String dateString = null;
        while (date == null && !valid) {
            dateString = MainMenu.scanner.nextLine();
            if (!dateString.isEmpty() && !dateString.equals("null")) {
                try {
                    date = LocalDate.parse(dateString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("La date est au mauvais format");
                }
            } else {
                valid = true;
            }
        }
        return dateString;
    }

    /**
     * Asks for a new date but with the possibility to keep the old one.
     * @param pDate the old date
     * @return a String of containing the new date, the old one or null
     */
    public static String inputNewDate(String pDate) {
        LocalDate date = null;
        boolean valid = false;
        String dateString = null;
        while (date == null && !valid) {
            dateString = MainMenu.scanner.nextLine();
            if (!dateString.isEmpty() && !dateString.equals("null")) {
                try {
                    date = LocalDate.parse(dateString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("La date est au mauvais format");
                }
            } else if (dateString.isEmpty()) {
                dateString = pDate;
                valid = true;
            } else {
                valid = true;
            }
        }
        return dateString;
    }

    /**
     * Manage the navigation in the list of objects.
     * @param pPageFilter the pPageFilter on which to operate
     * @return a boolean indicating if the operation was successful
     */
    public static boolean manageNavigation(PageFilter pPageFilter) {
        boolean ok = false;
        while (!ok) {
            int nextOption = MenuUtil.waitForInt();
            System.out.println(nextOption);
            if (nextOption == 1) {
                if (pPageFilter.getPageNum() - 1 >= 1) {
                    pPageFilter.setPageNum(pPageFilter.getPageNum() - 1);
                    ok = true;
                } else {
                    System.out.println("Vous êtes déjà sur la première page");
                    ok = false;
                }
            } else if (nextOption == 2) {
                if (pPageFilter.getPageNum() + 1 <= pPageFilter.getNbPage()) {
                    pPageFilter.setPageNum(pPageFilter.getPageNum() + 1);
                    ok = true;
                } else {
                    System.out.println("Vous êtes déjà sur la dernière page");
                    ok = false;
                }
            } else if (nextOption == 3) {
                MainMenu.scanner.nextLine();
                System.out.print("Entrez le numéro de la page :");
                String page = waitForLine();
                int newPage;
                try {
                    newPage = Integer.parseInt(page);
                } catch (NumberFormatException e) {
                    System.out.println("Vous devez entrer un nombre");
                    return false;
                }
                if (0 < newPage && newPage <= pPageFilter.getNbPage()) {
                    pPageFilter.setPageNum(newPage);
                    ok = true;
                } else {
                    System.out.println("Cette page n'existe pas");
                    ok = false;
                }
            } else if (nextOption == 4) {
                return false;
            }
        }
        return true;
    }
}