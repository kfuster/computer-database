package com.excilys.formation.service.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.HikariConnectionProvider;
import com.excilys.formation.persistence.jdbc.ComputerDaoJdbc;
import com.excilys.formation.service.ComputerService;
import ch.qos.logback.classic.Logger;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
public class ComputerServiceImpl implements ComputerService {
    final Logger logger = (Logger) LoggerFactory.getLogger(ComputerServiceImpl.class);
    private ComputerDao computerDao;
    private static ComputerServiceImpl computerService;
    private static Connection connection;
    /**
     * Constructor for ComputerServiceImpl. Initializes computerDao.
     */
    private ComputerServiceImpl() {
        computerDao = ComputerDaoJdbc.getInstance();
        try {
            connection = HikariConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            logger.error("Error init ComputerService : ", e);
        }
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
            return computerDao.create(connection, pComputer);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public boolean delete(long pId) {
        try {
            return computerDao.delete(connection, pId);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return false;
    }
    public boolean deleteList(String idList) {
        try {
            computerDao.deleteList(connection, idList);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }

        return true;
    }
    @Override
    public Computer getById(long pId) {
        try {
            return computerDao.getById(connection, pId);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public Page<Computer> getPage(PageFilter pViewDto) {
        try {
            return computerDao.getPage(connection, pViewDto);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public void update(Computer pComputer) {
        try {
            computerDao.update(connection, pComputer);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
    }
}
