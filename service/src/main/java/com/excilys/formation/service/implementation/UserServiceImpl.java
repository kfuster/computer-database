package com.excilys.formation.service.implementation;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.User;
import com.excilys.formation.persistence.UserDao;
import com.excilys.formation.service.UserService;

import ch.qos.logback.classic.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User create(User pUser){
        try {
            return userDao.create(pUser);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String pName) throws UsernameNotFoundException {
        User user = null;
        try {
            user =  userDao.getByName(pName);
        } catch (PersistenceException e) {
            LOGGER.error( "UserServiceImpl : loadUserByUsername() catched PersistenceException",e);
        }
        
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", pName));
        }
        return user;
    }
}