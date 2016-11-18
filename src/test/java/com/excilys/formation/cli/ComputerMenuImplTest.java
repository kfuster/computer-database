package com.excilys.formation.cli;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputerMenuImplTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        assertNotNull("Get instance : not null", computerMenu);
        assertEquals("Get instance : good class name", computerMenu.getClass().getSimpleName(), "ComputerMenuImpl");
    }
    @Test
    public void testStartMenu() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testList() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "1\n4\n6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testInfo() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "2\n\n\n6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testCreate() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "3\n\n6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testUpdate() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "4\n\n\n6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testDelete() {
        ComputerMenu computerMenu = ComputerMenuImpl.getInstance();
        String data = "5\n\n\n6\n";
        String data2 = "5\n585\n6\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();System.setIn(new ByteArrayInputStream(data2.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        computerMenu.startMenu();
        System.setIn(stdin);
    }
}