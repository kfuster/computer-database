package com.excilys.formation.service;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.ComputerDaoJdbc;

public class ComputerServiceImpl implements ComputerService{
    private ComputerDao computerDao;
    
    public ComputerServiceImpl(){
        computerDao = ComputerDaoJdbc.getInstance();
    }
    
    public Computer create(Computer pComputer) {
        try {
            return computerDao.create(pComputer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Computer getById(int pId) {
        return computerDao.getById(pId);
    }
    
    public void getPage(Page<Computer> pPage) {
        computerDao.getPage(pPage);
    }
}
