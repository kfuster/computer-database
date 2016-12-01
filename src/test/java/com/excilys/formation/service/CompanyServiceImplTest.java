package com.excilys.formation.service;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.implementation.CompanyServiceImpl;

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
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<Company> page = new Page<>(10);
        page = companyService.getPage(pageFilter);
        assertNotNull("Get page : page not null", page);
        assertNotNull("Get page : list not null", page.getElements());
        assertNotNull("Get page : first element not null", page.getElements().get(0));
        assertEquals("Get page : first element Company", page.getElements().get(0).getClass().getSimpleName(), "Company");
        assertNotNull("Get page : last element not null", page.getElements().get(page.getElements().size()-1));
        assertEquals("Get page : last element Company", page.getElements().get(page.getElements().size()-1).getClass().getSimpleName(), "Company");
    }
}