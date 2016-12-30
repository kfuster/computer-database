package com.excilys.formation.service;

import com.excilys.formation.model.User;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

public interface UserService extends BaseService<User> {
    /**
     * Get a User by its id.
     * @param pId the id of the User to get
     * @return the User obtained from the DB
     */
    User getById(long pId);

    /**
     * Populate a page of User according to the PageFilter parameters.
     * @param pPageFilter the PageFilter containing the parameters
     * @return the Page with the populated list
     */
    Page<User> getPage(PageFilter pPageFilter);
}