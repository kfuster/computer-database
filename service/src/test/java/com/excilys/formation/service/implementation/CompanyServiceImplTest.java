package com.excilys.formation.service.implementation;

import com.excilys.formation.config.ServiceSpringTestConfig;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ookami on 07/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ServiceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:dataset.xml")
public class CompanyServiceImplTest {
    @Autowired
    CompanyService companyService;

    @Test
    public void getPage() throws Exception {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setPageNum(1);
        pageFilter.setElementsByPage(10);
        pageFilter.addCondition("computerName", "Test");
        pageFilter.addCondition("companyName", "Test");
        pageFilter.addCondition("column", "name");
        pageFilter.addCondition("order", "ASC");
        Page<Company> companies = companyService.getPage(pageFilter);
        assertNotNull(companies);
        assertNotNull(companies.getElements());
        assertTrue(companies.getTotalPages() == 1);
        assertTrue(companies.getElements().size() == 2);
        assertEquals(companies.getElements().get(0).getName(), "Company test 1");

        try {
            companies = companyService.getPage(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteCompany() throws Exception {
        companyService.delete((long)1);
        Company company = companyService.getById((long)1);
        assertNull(company);
    }

    @Test
    public void getAllCompanies_ShouldReturnCompanyList() throws Exception {
        List<Company> companies = companyService.getAll();
        assertNotNull(companies);
        assertTrue(companies.size() == 2);
    }

    @Test
    public void getCompanyById_ShouldReturnCompanyOrNull() throws Exception {
        Company company = companyService.getById((long)1);
        assertNotNull(company);
        assertEquals(company.getName(), "Company test 1");
        company = companyService.getById((long)3);
        assertNull(company);
    }

}