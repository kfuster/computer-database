package com.excilys.formation.service.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.HikariConnectionProvider;
import com.excilys.formation.persistence.jdbc.CompanyDaoJdbc;
import com.excilys.formation.persistence.jdbc.ComputerDaoJdbc;
import com.excilys.formation.service.CompanyService;
import ch.qos.logback.classic.Logger;

/**
 * Manages Company services.
 * @author kfuster
 *
 */
public class CompanyServiceImpl implements CompanyService {
    final Logger logger = (Logger) LoggerFactory.getLogger(CompanyServiceImpl.class);
    private CompanyDao companyDao;
    private ComputerDao computerDao;
    private static CompanyServiceImpl companyService;
    private static Connection connection;
    /**
     * Constructor for CompanyServiceImpl.
     * Initializes the companyDao.
     */
    private CompanyServiceImpl(){
        companyDao = CompanyDaoJdbc.getInstance();
        computerDao = ComputerDaoJdbc.getInstance();
        try {
            connection = HikariConnectionProvider.getInstance().getConnection();
        } catch (SQLException e) {
            logger.error("Error init CompanyService : ", e);
        }
    }
    /**
     * Getter for the CompanyServiceImpl instance.
     * Initializes it if null.
     * @return the instance of CompanyServiceImpl
     */
    public static CompanyServiceImpl getInstance(){
        if (companyService == null) {
            companyService = new CompanyServiceImpl();
        }
        return companyService;
    }
    @Override
    public Page<Company> getPage(PageFilter pPageFilter) {
        try {
            return companyDao.getPage(connection, pPageFilter);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public boolean delete(long pId) throws ServiceException {
        try {
            connection.setAutoCommit(false);
            computerDao.deleteByCompany(connection, pId);
            companyDao.delete(connection, pId);
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (PersistenceException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Error CompanyService : delete : ", e1);
            }
            logger.info(e.getMessage());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("Error CompanyService : delete : ", e1);
            }
            logger.error("Error CompanyService : delete : ", e);
            throw new ServiceException("Erreur lors de la suppression de la companie");
        }
        return false;
    }
    @Override
    public List<Company> getAll() {
        List<Company> allCompanies = null;
        try {
            allCompanies = companyDao.getAll(connection);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return allCompanies;
    }
}