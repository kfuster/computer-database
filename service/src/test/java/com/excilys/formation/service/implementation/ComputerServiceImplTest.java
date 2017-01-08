package com.excilys.formation.service.implementation;

import com.excilys.formation.config.ServiceSpringTestConfig;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ookami on 07/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:dataset.xml")
public class ComputerServiceImplTest {
    @Autowired
    private ComputerService computerService;

    @Test
    public void createComputer_ShouldReturnComputerOrNull() throws Exception {
        Computer newComputer = new Computer.ComputerBuilder("Test Computer 3").dateIntro(LocalDate.parse("1994-04-04"))
                .dateDisc(LocalDate.parse("1995-04-05")).build();
        computerService.create(newComputer);
        assertTrue(newComputer.getId() != null);
        assertTrue(newComputer.getId() != 0);
        try {
            computerService.create(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteComputer() throws Exception {
        computerService.delete((long)1);
        Computer computer = computerService.getById((long)1);
        assertNull(computer);
    }

    @Test
    public void deleteList() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add((long) 1);
        ids.add((long) 2);
        computerService.deleteList(ids);
        Computer computer = computerService.getById((long)1);
        assertNull(computer);
        computer = computerService.getById((long)2);
        assertNull(computer);

    }

    @Test
    public void getComputerById_ShouldReturnComputerOrNull() throws Exception {
        Computer computer = null;
        computer = computerService.getById((long)1);
        assertNotNull(computer);
        assertEquals((long)computer.getId(), (long)1);
        assertEquals(computer.getName(), "Test Computer 1");
    }

    @Test
    public void getComputerPage_ShouldReturnComputerPageOrThrowIllegalArgumentException() throws Exception {
        Page<Computer> computers = null;
        PageFilter pageFilter = new PageFilter();
        pageFilter.setPageNum(1);
        pageFilter.setElementsByPage(10);
        pageFilter.addCondition("computerName", "Test");
        pageFilter.addCondition("companyName", "Test");
        pageFilter.addCondition("column", "name");
        pageFilter.addCondition("order", "ASC");
        computers = computerService.getPage(pageFilter);
        assertNotNull(computers);
        assertNotNull(computers.getElements());
        assertTrue(computers.getElements().size() == 2);
        assertEquals(computers.getElements().get(0).getName(), "Test Computer 1");

        try {
            computers = computerService.getPage(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void updateComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder("Test Computer 1").id((long) 1).dateIntro(LocalDate.parse("1991-02-02"))
                .dateDisc(LocalDate.parse("1994-05-05")).build();
        computerService.update(computer);
        computer = computerService.getById((long) 1);
        assertNull(computer.getCompany());
        try {
            computerService.update(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

}