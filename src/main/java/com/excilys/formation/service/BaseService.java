package com.excilys.formation.service;

import com.excilys.formation.exception.ServiceException;

public interface BaseService<T> {
    default T create(T t) throws ServiceException {
        throw new UnsupportedOperationException("create() not implemented");
    };
    
    default void update(T t) throws ServiceException {
        throw new UnsupportedOperationException("update() not implemented");
    };
    
    default void delete(int idToDelete) throws ServiceException {
        throw new UnsupportedOperationException("delete() not implemented");
    };
}