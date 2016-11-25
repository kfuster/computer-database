package com.excilys.formation.persistence;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.jdbc.CompanyDaoJdbc;

public class CompanyDaoJdbcTest {
    private CompanyDao companyDao;
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        companyDao = CompanyDaoJdbc.getInstance();
        assertNotNull("Get instance : not null", companyDao);
        assertEquals("Get instance : good class name", companyDao.getClass().getSimpleName(), "CompanyDaoJdbc");
    }
    @Test
    public void testGetById() {
        companyDao = CompanyDaoJdbc.getInstance();
        Company company = null;
        try {
             company = companyDao.getById(2);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Get by id : not null", company);
        assertTrue("Get by id : good id", company.getId() == 2);
    }
    @Test
    public void testGetPage() {
        companyDao = CompanyDaoJdbc.getInstance();
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<Company> page = new Page<>(10);
        try {
            page = companyDao.getPage(pageFilter);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Get page : list not null", page.elems);
        assertNotNull("Get page : first element not null", page.elems.get(0));
        assertEquals("Get page : first element Company", page.elems.get(0).getClass().getSimpleName(), "Company");
        assertNotNull("Get page : last element not null", page.elems.get(page.elems.size()-1));
        assertEquals("Get page : last element Company", page.elems.get(page.elems.size()-1).getClass().getSimpleName(), "Company");
    }
}
