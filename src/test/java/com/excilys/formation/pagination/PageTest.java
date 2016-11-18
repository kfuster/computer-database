package com.excilys.formation.pagination;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PageTest {
    private Page<Object> page;
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testPage() {
        page = new Page<>(10);
        assertNotNull("New page : not null", page);
        assertTrue("New page", page.elemByPage == 10);
    }
    @Test
    public void testNextPage() {
        page = new Page<>(10);
        page.setTotalElement(15);
        page.nextPage();
        assertTrue("Next page", page.page == 2);
        page.nextPage();
        assertTrue("Next page", page.page == 2);
    }
    @Test
    public void testPrevPage() {
        page = new Page<>(10);
        page.setTotalElement(50);
        page.nextPage();
        page.prevPage();
        assertTrue("Next page", page.page == 1);
        page.prevPage();
        assertTrue("Next page", page.page == 1);
    }
    @Test
    public void testSetPage() {
        page = new Page<>(10);
        page.setTotalElement(50);
        page.trySetPage(3);
        assertTrue("Next page", page.page == 3);
        page.trySetPage(13);
        assertTrue("Next page", page.page == 3);
    }
    @Test
    public void testSetTotalElem() {
        page = new Page<>(10);
        page.setTotalElement(50);
        assertTrue("Set total elem : total elements", page.totalElement == 50);
        assertTrue("Set total elem : nb pages", page.nbPages == 5);
    }
    @Test
    public void testSetElemenByPage() {
        page = new Page<>(10);
        page.setElemByPage(20);
        page.setTotalElement(100);
        assertTrue("Set total elem : total elements", page.totalElement == 100);
        assertTrue("Set total elem : nb pages", page.nbPages == 5);
    }
}