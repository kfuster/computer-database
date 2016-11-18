package com.excilys.formation.service;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.pagination.Page;

public class CompanyServiceImplTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        assertNotNull("Get instance : not null", companyService);
        assertEquals("Get instance : good class name", companyService.getClass().getSimpleName(), "CompanyServiceImpl");
    }
    @Test
    public void testGetPage() {
        CompanyService companyService = CompanyServiceImpl.getInstance();
        Page<CompanyDto> page = new Page<>(10);
        page = companyService.getPage(page);
        assertNotNull("Get page : page not null", page);
        assertNotNull("Get page : list not null", page.elems);
        assertNotNull("Get page : first element not null", page.elems.get(0));
        assertEquals("Get page : first element Company", page.elems.get(0).getClass().getSimpleName(), "CompanyDto");
        assertNotNull("Get page : last element not null", page.elems.get(page.elems.size()-1));
        assertEquals("Get page : last element Company", page.elems.get(page.elems.size()-1).getClass().getSimpleName(), "CompanyDto");
    }
}