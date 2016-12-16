package com.excilys.formation.service.implementation;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.ComputerService;
import ch.qos.logback.classic.Logger;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
@Service
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerServiceImpl.class);
    @Autowired
    private ComputerDao computerDao;

    public void setComputerDao(ComputerDao pComputerDao) {
        computerDao = pComputerDao;
    }

    @Override
    public Computer create(Computer pComputer) {
        try {
            return computerDao.create(pComputer);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long pId) {
        try {
            computerDao.delete(pId);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void deleteList(String idList) {
        try {
            computerDao.deleteList(idList);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public Computer getById(long pId) {
        try {
            return computerDao.getById(pId);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Page<Computer> getPage(PageFilter pViewDto) {
        try {
            return computerDao.getPage(pViewDto);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Computer pComputer) {
        try {
            computerDao.update(pComputer);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
