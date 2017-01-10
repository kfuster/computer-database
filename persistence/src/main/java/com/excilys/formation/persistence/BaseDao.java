package com.excilys.formation.persistence;

import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;

/**
 * Interface for DAO classes.
 * @author kfuster
 * @param <T> The Selected entity
 */
public interface BaseDao<T> {
    /**
     * Method to save an object in the DB.
     * @param t the object to save
     * @return the object created
     */
    default T create(T t) {
        throw new UnsupportedOperationException("create() not implemented");
    }

    /**
     * Method to update an object in the DB.
     * @param t the object to update
     */
    default void update(T t) {
        throw new UnsupportedOperationException("update() not implemented");
    }

    /**
     * Method to delete an object in the DB.
     * @param idToDelete ID of the object to delete
     */
    default void delete(long idToDelete) {
        throw new UnsupportedOperationException("delete() not implemented");
    }

    /**
     * Method to get a page of elements.
     * @param pageFilter the PageFilter containing the useful informations
     * @return the obtained page
     */
    Page<T> getPage(PageFilter pageFilter);
}