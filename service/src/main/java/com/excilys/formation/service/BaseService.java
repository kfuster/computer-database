package com.excilys.formation.service;

/**
 * Basic service interface.
 * @author kfuster
 */
public interface BaseService<T> {

    /**
     * Creates a object in the DB by passing the data from a DTO to an pojo and
     * to the DAO.
     * @param t a DTO of the object to create
     * @return a DTO of the object created
     */
    default T create(T t) {
        throw new UnsupportedOperationException("create() not implemented");
    }

    /**
     * Updates a object in the DB by passing the data from a DTO to an pojo and
     * to the DAO.
     * @param t a DTO of the object to update
     */
    default void update(T t) {
        throw new UnsupportedOperationException("update() not implemented");
    }

    /**
     * Deletes an object in the DB by sending it's Id to the dao.
     * @param idToDelete the id of the object to delete
     */
    default void delete(long idToDelete) {
        throw new UnsupportedOperationException("delete() not implemented");
    }
}