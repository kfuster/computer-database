package com.excilys.formation.service.implementation;

import com.excilys.formation.config.ServiceSpringTestConfig;
import com.excilys.formation.model.User;
import com.excilys.formation.service.UserService;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ookami on 07/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceSpringTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:dataset.xml")
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void createUser_ShouldReturnUser() throws Exception {
        User user = new User.UserBuilder().username("user test 2")
                .password("pass2").build();
        userService.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());

        try {
            userService.create(null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

}