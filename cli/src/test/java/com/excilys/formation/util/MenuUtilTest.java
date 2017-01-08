package com.excilys.formation.util;

import com.excilys.formation.cli.MainMenu;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Ookami on 08/01/2017.
 */
public class MenuUtilTest {
    @Test
    public void waitForLine_ShouldReturnString() throws Exception {
        MainMenu.scanner = new Scanner("test\n");
        String test = MenuUtil.waitForLine();
        assertNotNull(test);
        assertEquals(test, "test");
    }

    @Test
    public void waitForInt_ShouldReturnInt() throws Exception {
        MainMenu.scanner = new Scanner("545\n");
        int test = MenuUtil.waitForInt();
        assertNotNull(test);
        assertEquals(test, 545);
    }

    @Test
    public void inputDate() throws Exception {
        MainMenu.scanner = new Scanner("1991-05-04\nnull\n");
        String test = MenuUtil.inputDate();
        assertNotNull(test);
        assertEquals(test, "1991-05-04");
        test = MenuUtil.inputDate();
        assertEquals(test, "null");
    }

    @Test
    public void inputNewDate() throws Exception {
        MainMenu.scanner = new Scanner("1991-05-04\n\nnull\n");
        String test = MenuUtil.inputNewDate("1991-04-03");
        assertNotNull(test);
        assertEquals(test, "1991-05-04");
        test = MenuUtil.inputNewDate("1989-09-09");
        assertNotNull(test);
        assertEquals(test, "1989-09-09");
        test = MenuUtil.inputNewDate("1989-09-09");
        assertEquals(test, "null");
    }
}