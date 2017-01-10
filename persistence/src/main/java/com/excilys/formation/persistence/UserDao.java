package com.excilys.formation.persistence;

import com.excilys.formation.model.User;

/**
 * Interface representing the DAO of a User.
 */
public interface UserDao extends BaseDao<User>{

    /**
     * Get a user by its name.
     * @param name the name of the User to get
     * @return a User or null
     */
    User getByName(String name);

    /**
     * Get a User by its id.
     * @param id the id of the User to get
     * @return a User or null
     */
    User getById(long id);
}