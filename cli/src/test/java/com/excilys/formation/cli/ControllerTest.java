package com.excilys.formation.cli;

import com.excilys.formation.config.CliSpringTestConfig;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.junit.Assert.*;

/**
 * Created by Ookami on 08/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {CliSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class ControllerTest {
    @Autowired
    private Controller controller;
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8180);
    @Test
    public void getPageCompany_ShouldReturnCompanyDtoPage() throws Exception {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<CompanyDto> testPage = controller.getPageCompany(pageFilter);
        assertNotNull(testPage);
        assertNotNull(testPage.getElements());
        assertTrue(testPage.getElements().size() == 10);
    }

    @Test
    public void createComputer_ShouldReturnComputerDtoWithId() throws Exception {
        ComputerDto computerDtoInit = new ComputerDto.ComputerDtoBuilder("test computer").build();
        ComputerDto computerDto = controller.createComputer(computerDtoInit);
        assertNotNull(computerDto);
        assertTrue(computerDto.getId() != null);
        assertTrue(computerDto.getId() != 0);
    }

    @Test
    public void getPageComputer_ShouldReturnComputerDtoPage() throws Exception {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<ComputerDto> testPage = controller.getPageComputer(pageFilter);
        assertNotNull(testPage);
        assertNotNull(testPage.getElements());
        assertTrue(testPage.getElements().size() == 10);
    }

    @Test
    public void getComputerById_ShouldReturnComputerDto() throws Exception {
        ComputerDto computerDto = controller.getComputerById((long)2);
        assertNotNull(computerDto);
        assertEquals(computerDto.getName(), "test computer");
    }
}