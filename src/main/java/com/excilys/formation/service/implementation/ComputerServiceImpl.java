package com.excilys.formation.service.implementation;

import java.util.List;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.jdbc.ComputerDaoJdbc;
import com.excilys.formation.service.ComputerService;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
public class ComputerServiceImpl implements ComputerService {
    private ComputerDao computerDao;
    private static ComputerServiceImpl computerService;
    /**
     * Constructor for ComputerServiceImpl. Initializes computerDao.
     */
    private ComputerServiceImpl() {
        computerDao = ComputerDaoJdbc.getInstance();
    }
    /**
     * Getter for the ComputerServiceImpl instance. Initializes it if null.
     * @return the instance of ComputerServiceImpl
     */
    public static ComputerServiceImpl getInstance() {
        if (computerService == null) {
            computerService = new ComputerServiceImpl();
        }
        return computerService;
    }
    @Override
    public Computer create(Computer pComputer) {
        try {
            return computerDao.create(pComputer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean delete(long pId) {
        try {
            return computerDao.delete(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteList(List<Integer> ids) {
        for (Integer id : ids) {
            try {
                computerDao.delete(id);
            } catch (PersistenceException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public Computer getById(long pId) {
        try {
            return computerDao.getById(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Page<Computer> getPage(PageFilter pViewDto) {
        return getPageWithFilter(pViewDto, null);
    }
    public Page<Computer> getPageWithFilter(PageFilter pViewDto, String pFilter) {
        try {
            return computerDao.getPage(pViewDto, pFilter);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void update(Computer pComputer) {
        try {
            computerDao.update(pComputer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
