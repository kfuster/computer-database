package com.excilys.formation.persistence;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;

/**
 * Interface for DAO classes.
 * @author kfuster
 *
 * @param <T> The Selected entity
 */
public interface BaseDao<T> {
    /**
     * Method to save an object in the DB.
     * @param t the object to save
     * @throws PersistenceException in case of problems while creating
     * @return the object created
     */
    default T create(T t) throws PersistenceException {
        throw new UnsupportedOperationException("create() not implemented");
    };
    /**
     * Method to update an object in the DB.
     * @param t the object to update
     * @throws PersistenceException in case of problems while updating
     */
    default void update(T t) throws PersistenceException {
        throw new UnsupportedOperationException("update() not implemented");
    };
    /**
     * Method to delete an object in the DB.
     * @param idToDelete ID of the object to delete
     * @throws PersistenceException in case of problems while deleting
     */
    default void delete(int idToDelete) throws PersistenceException {
        throw new UnsupportedOperationException("delete() not implemented");
    };
    /**
     * Method to get a page of elements.
     * @param pPage the Page to get
     * @throws PersistenceException in case of problems while getting the page
     * @return the obtained page
     */
    Page<T> getPage(Page<T> pPage) throws PersistenceException;
}