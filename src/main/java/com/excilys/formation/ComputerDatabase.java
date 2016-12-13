package com.excilys.formation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import com.excilys.formation.cli.MainMenu;

/**
 * Main class, launch the menu.
 * @author kfuster
 *
 */
@Component
public class ComputerDatabase {
    public static ApplicationContext context;
    @Autowired
    public MainMenu mainMenu;
    /**
     * Launches the main menu.
     * @param args a list of arguments
     */
    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("application-context.xml");
        try {
            //((MainMenu)context.getBean("mainMenu")).startMenu();
        } catch (BeansException e) {
        }
        ComputerDatabase computerDatabase = context.getBean(ComputerDatabase.class);
        computerDatabase.start(args);
        //mainMenu.startMenu();
    }
    
    public void setMainMenu(MainMenu pMainMenu) {
        mainMenu = pMainMenu;
    }
    
    private void start(String[] args) {
        mainMenu.startMenu();
    }
}