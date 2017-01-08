package com.excilys.formation.persistence;

import com.excilys.formation.model.User;

public interface UserDao extends BaseDao<User>{
    /**
     * Get a user by its name.
     * @param pName the name of the User to get
     * @return a User or null
     */
    User getByName(String pName);

    /**
     * Get a User by its id.
     * @param pId the id of the User to get
     * @return a User or null
     */
    User getById(long pId);
}