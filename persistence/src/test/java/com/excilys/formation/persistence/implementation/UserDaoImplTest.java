package com.excilys.formation.persistence.implementation;

import com.excilys.formation.config.PersistenceSpringTestConfig;
import com.excilys.formation.model.User;
import com.excilys.formation.persistence.UserDao;
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
public class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void createUser_ShouldReturnUser() throws Exception {
        User user = new User.UserBuilder().username("user test 2")
                                            .password("pass2").build();
        userDao.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        try {
            userDao.create(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getUserPage_ShouldThrowException() throws Exception {
        try {
            userDao.getPage(null);
        } catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getUserByName_ShouldReturnUserOrNull() throws Exception {
        User user = userDao.getByName("user test");
        assertNotNull(user);
        assertEquals(user.getPassword(), "pass test" );
        assertTrue(user.getAuthorities().size() == 1);
        user = userDao.getByName("Doesn't exist");
        assertNull(user);
    }

    @Test
    public void getUserById_ShouldReturnUserOrNull() throws Exception {
        User user = userDao.getById((long)1);
        assertNotNull(user);
        assertEquals(user.getUsername(), "user test" );
        user = userDao.getById((long)2);
        assertNull(user);
    }

}