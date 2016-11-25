package com.excilys.formation.persistence;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.jdbc.ComputerDaoJdbc;

public class ComputerDaoJdbcTest {
    private ComputerDao computerDao;
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGetInstance() {
        computerDao = ComputerDaoJdbc.getInstance();
        assertNotNull("Get instance : not null", computerDao);
        assertEquals("Get instance : good class name", computerDao.getClass().getSimpleName(), "ComputerDaoJdbc");
    }
    @Test
    public void testCreate() {
        computerDao = ComputerDaoJdbc.getInstance();
        Computer computer = new Computer.ComputerBuilder("New computer").dateIntro(LocalDate.parse("1978-03-22")).dateDisc(LocalDate.parse("1989-06-14")).company(new Company.CompanyBuilder("New company").id(5).build()).build();
        try {
            computer = computerDao.create(computer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Create : not null", computer);
        assertTrue("Create : new id", computer.getId() >=1);
    }
    @Test
    public void testUpdate() {
        computerDao = ComputerDaoJdbc.getInstance();
        Computer computer = new Computer.ComputerBuilder("New computer").dateIntro(LocalDate.parse("1978-03-22")).dateDisc(LocalDate.parse("1989-06-14")).company(new Company.CompanyBuilder("New company").id(5).build()).build();
        Computer testComputer = null; 
        try {
            computer = computerDao.create(computer);
            computer.setName("Update name");
            computerDao.update(computer);
            testComputer = computerDao.getById(computer.getId());
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Update : not null", testComputer);
        assertEquals("Update : new name", computer.getName(), testComputer.getName());
    }
    @Test
    public void testDelete() {
        computerDao = ComputerDaoJdbc.getInstance();
        Computer computer = new Computer.ComputerBuilder("New computer").dateIntro(LocalDate.parse("1978-03-22")).dateDisc(LocalDate.parse("1989-06-14")).company(new Company.CompanyBuilder("New company").id(5).build()).build();
        Computer testComputer = null; 
        long idToDelete = 0;
        try {
            computer = computerDao.create(computer);
            idToDelete = computer.getId();
            computerDao.delete(idToDelete);
            testComputer = computerDao.getById(idToDelete);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNull("Delete : computer null", testComputer);
    }
    @Test
    public void testGetById() {
        computerDao = ComputerDaoJdbc.getInstance();
        Computer computer = null;
        try {
            computer = computerDao.getById(24);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Get by id : not null", computer);
        assertTrue("Get by id : good id", computer.getId() == 24);
        assertNotNull("Get by id : name not null", computer.getName());
    }
    @Test
    public void testGetByName() {
        computerDao = ComputerDaoJdbc.getInstance();
        Computer computer = null;
        try {
            computer = computerDao.getByName("Macintosh Quadra");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Get by id : not null", computer);
        assertEquals("Get by id : name not null", computer.getName(), "Macintosh Quadra");
    }
    @Test
    public void testGetPage() {
        computerDao = ComputerDaoJdbc.getInstance();
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        Page<Computer> page = new Page<>(10);
        try {
            page = computerDao.getPage(pageFilter);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        assertNotNull("Get page : list not null", page.elems);
        assertNotNull("Get page : first element not null", page.elems.get(0));
        assertEquals("Get page : first element Company", page.elems.get(0).getClass().getSimpleName(), "Computer");
        assertNotNull("Get page : last element not null", page.elems.get(page.elems.size()-1));
        assertEquals("Get page : last element Company", page.elems.get(page.elems.size()-1).getClass().getSimpleName(), "Computer");
    }
}