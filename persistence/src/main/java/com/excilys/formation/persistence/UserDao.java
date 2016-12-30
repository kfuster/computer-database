package com.excilys.formation.persistence;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.User;

public interface UserDao extends BaseDao<User>{
    /**
     * Get a user by its name.
     * @param pName the name of the User to get
     * @return a User or null
     * @throws PersistenceException in case of problems while getting the
     *             User
     */
    User getByName(String pName) throws PersistenceException;

    /**
     * Get a User by its id.
     * @param pId the id of the User to get
     * @return a User or null
     * @throws PersistenceException in case of problems while getting the
     *             user <class>User</class>
     */
    User getById(long pId) throws PersistenceException;
}