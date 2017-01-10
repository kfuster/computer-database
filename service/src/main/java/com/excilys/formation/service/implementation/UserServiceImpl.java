package com.excilys.formation.service.implementation;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.model.User;
import com.excilys.formation.persistence.UserDao;
import com.excilys.formation.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Users.
 */
@Service("UserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User create(User user){
        return userDao.create(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDao.getByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", name));
        }
        return user;
    }
}