package com.excilys.formation.service;

import com.excilys.formation.exception.ServiceException;

/**
 * Basic service interface.
 * @author kfuster
 *
 */
public interface BaseService<T> {
    /**
     * Creates a object in the DB by passing the data from a DTO to an pojo and to the DAO.
     * @param t a DTO of the object to create
     * @return a DTO of the object created
     * @throws ServiceException in case of problems during the creation
     */
    default T create(T t) throws ServiceException {
        throw new UnsupportedOperationException("create() not implemented");
    };
    /**
     * Updates a object in the DB by passing the data from a DTO to an pojo and to the DAO.
     * @param t a DTO of the object to update
     * @throws ServiceException in case of problems during the update
     */
    default void update(T t) throws ServiceException {
        throw new UnsupportedOperationException("update() not implemented");
    };
    /**
     * Deletes an object in the DB by sending it's Id to the dao.
     * @param idToDelete the id of the object to delete
     * @throws ServiceException in case of problems during the delete
     */
    default void delete(int idToDelete) throws ServiceException {
        throw new UnsupportedOperationException("delete() not implemented");
    };
}