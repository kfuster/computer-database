package com.excilys.formation.persistence.implementation;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import ch.qos.logback.classic.Logger;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.excilys.formation.config.PersistenceSpringTestConfig;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.persistence.ComputerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {PersistenceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:computers_entries.xml")
public class ComputerDaoImplTest {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerDaoImplTest.class);
    @Autowired
    private ComputerDao computerDao;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /*@Test
    public void testCreateComputer_ShouldReturnComputer() {
        
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteList() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByCompany() {
        fail("Not yet implemented");
    }*/

    @Test
    public void testGetById() {
        Computer computer = null;
        try {
            computer = computerDao.getById(1);
        } catch (PersistenceException e) {
            LOGGER.error( "ComputerDaoImplTest : testGetById() catched PersistenceException",e);
        }
        assertNotNull(computer);
    }

    /*@Test
    public void testGetByName() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPage() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddConditions() {
        fail("Not yet implemented");
    }*/
}
