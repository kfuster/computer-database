package com.excilys.formation.cli;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompanyMenuImplTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        CompanyMenu companyMenu = CompanyMenuImpl.getInstance();
        assertNotNull("Get instance : not null", companyMenu);
        assertEquals("Get instance : good class name", companyMenu.getClass().getSimpleName(), "CompanyMenuImpl");
    }
    @Test
    public void testStartMenu() {
        CompanyMenu companyMenu = CompanyMenuImpl.getInstance();
        String data = "2\n3\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        companyMenu.startMenu();
        System.setIn(stdin);
    }
    @Test
    public void testList() {
        CompanyMenu companyMenu = CompanyMenuImpl.getInstance();
        String data = "1\n4\n2\n3\n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        MainMenu.scanner = new Scanner(System.in);
        companyMenu.startMenu();
        System.setIn(stdin);
    }
}