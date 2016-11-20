package com.excilys.formation.cli.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.pagination.Page;

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
        String string = MainMenu.scanner.nextLine();
        return string;
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
     * Checks if a string is an integer.
     * @param pStringToCheck the String to check
     * @return a boolean
     */
    public static boolean isInteger(String pStringToCheck) {
        if (pStringToCheck == null) {
            return false;
        }
        int length = pStringToCheck.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        // we check if the string starts with "-" and the length is > 1
        if (pStringToCheck.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        // we check each char of the string to see if it's a number
        for (; i < length; i++) {
            char c = pStringToCheck.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    /**
     * Manage the page navigation in the menus.
     * @param pPage the Page on which to operate
     * @return a boolean indicating if the operation was successful
     */
    public static boolean manageNavigation(Page<?> pPage) {
        boolean ok = false;
        while (!ok) {
            int nextOption = MenuUtil.waitForInt();
            System.out.println(nextOption);
            if (nextOption == 1) {
                ok = pPage.prevPage();
                if (!ok) {
                    System.out.println("Vous êtes déjà sur la première page");
                }
            } else if (nextOption == 2) {
                ok = pPage.nextPage();
                if (!ok) {
                    System.out.println("Vous êtes déjà sur la dernière page");
                }
            } else if (nextOption == 3) {
                MainMenu.scanner.nextLine();
                System.out.print("Entrez le numéro de la page :");
                String page = "";
                while (page.isEmpty() && !MenuUtil.isInteger(page)) {
                    page = MainMenu.scanner.nextLine();
                }
                ok = pPage.trySetPage(Integer.parseInt(page));
                if (!ok) {
                    System.out.println("Cette page n'existe pas");
                }
            } else if (nextOption == 4) {
                return false;
            }
        }
        return true;
    }
}