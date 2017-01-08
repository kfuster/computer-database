package com.excilys.formation.persistence.implementation;

import com.excilys.formation.config.PersistenceSpringTestConfig;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.ComputerDao;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ookami on 07/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {PersistenceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:dataset.xml")
@Transactional
public class CompanyDaoImplTest {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;

    @Test
    public void getCompanyById_ShouldReturnCompany() throws Exception {
        Company company = companyDao.getById((long)2);
        assertNotNull(company);
        assertEquals((long)company.getId(), (long)2);
        assertEquals(company.getName(), "Company test 2");
    }

    @Test
    public void deleteCompany() throws Exception {
        try {
            companyDao.delete((long) 1);
        } catch (DataIntegrityViolationException e) {
            assertTrue(true);
        }
        computerDao.deleteByCompany((long)1);
        companyDao.delete((long) 1);
        Company company = new Company.CompanyBuilder("test").build();
        company = companyDao.getById((long)1);
        assertNull(company);
    }

    @Test
    public void getCompanyPage_ShouldReturnCompanyPage() throws Exception {
        Page<Company> companies = null;
        PageFilter pageFilter = new PageFilter();
        pageFilter.setPageNum(1);
        pageFilter.setElementsByPage(10);
        pageFilter.addCondition("computerName", "Test");
        pageFilter.addCondition("companyName", "Test");
        pageFilter.addCondition("column", "name");
        pageFilter.addCondition("order", "ASC");
        companies = companyDao.getPage(pageFilter);
        assertNotNull(companies);
        assertNotNull(companies.getElements());
        assertTrue(companies.getTotalPages() == 1);
        assertTrue(companies.getElements().size() == 2);
        assertEquals(companies.getElements().get(0).getName(), "Company test 1");

        try {
            companies = companyDao.getPage(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @Test
    public void getAllCompanies_ShouldReturnCompanyList() throws Exception {
        List<Company> companies = companyDao.getAll();
        assertNotNull(companies);
        assertTrue(companies.size() == 2);
    }

}