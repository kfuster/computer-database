package com.excilys.formation.persistence.implementation;

import com.excilys.formation.config.PersistenceSpringTestConfig;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {PersistenceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:dataset.xml")
@Transactional
public class ComputerDaoImplTest {
    @Autowired
    private ComputerDao computerDao;

    @Test
    public void createComputer_ShouldReturnComputer() {
        Computer newComputer = new Computer.ComputerBuilder("Test Computer 3").dateIntro(LocalDate.parse("1994-04-04"))
                .dateDisc(LocalDate.parse("1995-04-05")).build();
        computerDao.create(newComputer);
        assertTrue(newComputer.getId() != null);
        assertTrue(newComputer.getId() != 0);
        try {
            computerDao.create(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void updateComputer() {
        Computer computer = new Computer.ComputerBuilder("Test Computer 1").id((long) 1).dateIntro(LocalDate.parse("1991-02-02"))
                .dateDisc(LocalDate.parse("1994-05-05")).build();
        computerDao.update(computer);
        computer = computerDao.getById((long)1);
        assertNull(computer.getCompany());
        try {
            computerDao.update(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteComputer() {
        computerDao.delete((long)1);
        Computer computer = computerDao.getById((long)1);
        assertNull(computer);
    }

    /*@Test
    public void testDeleteList() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByCompany() {
        fail("Not yet implemented");
    }*/

    @Test
    public void getComputerById_ShouldReturnComputer() {
        Computer computer = computerDao.getById((long)1);
        assertNotNull(computer);
        assertEquals((long)computer.getId(), (long)1);
        assertEquals(computer.getName(), "Test Computer 1");
    }

    @Test
    public void getComputerByName_ShouldReturnComputer() {
        Computer computer = computerDao.getByName("Test Computer 2");
        assertNotNull(computer);
        assertEquals((long)computer.getId(), (long)2);
        assertEquals(computer.getName(), "Test Computer 2");
    }

    @Test
    public void getComputerPage_ShouldReturnComputerPage() {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setPageNum(1);
        pageFilter.setElementsByPage(10);
        pageFilter.addCondition("computerName", "Test");
        pageFilter.addCondition("companyName", "Test");
        pageFilter.addCondition("column", "name");
        pageFilter.addCondition("order", "ASC");
        Page<Computer> computers = computerDao.getPage(pageFilter);
        assertNotNull(computers);
        assertNotNull(computers.getElements());
        assertTrue(computers.getElements().size() == 2);
        assertEquals(computers.getElements().get(0).getName(), "Test Computer 1");
        try {
        computers = computerDao.getPage(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

}
