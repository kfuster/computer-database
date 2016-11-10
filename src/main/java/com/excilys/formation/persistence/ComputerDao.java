package com.excilys.formation.persistence;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;

public interface ComputerDao extends BaseDao<Computer> {
    default Computer getByName(String name) throws PersistenceException {
        throw new UnsupportedOperationException("getByName() not implemented");
    }
    
    Computer getById(int id);
}