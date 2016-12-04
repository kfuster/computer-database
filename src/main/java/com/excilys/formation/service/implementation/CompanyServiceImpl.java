package com.excilys.formation.service.implementation;

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
public enum CompanyServiceImpl implements CompanyService {
    INSTANCE;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CompanyServiceImpl.class);
    private CompanyDao companyDao = CompanyDaoJdbc.INSTANCE;
    private ComputerDao computerDao = ComputerDaoJdbc.INSTANCE;
    private static HikariConnectionProvider hikariConnectionProvider = HikariConnectionProvider.INSTANCE;

    @Override
    public Page<Company> getPage(PageFilter pPageFilter) {
        try {
            return companyDao.getPage(pPageFilter);
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(long pId) throws ServiceException {
        try {
            hikariConnectionProvider.initConnection();
            hikariConnectionProvider.initTransaction();
            computerDao.deleteByCompany(hikariConnectionProvider.getConnection(), pId);
            companyDao.delete(hikariConnectionProvider.getConnection(), pId);
            hikariConnectionProvider.commit();
            hikariConnectionProvider.closeConnection();
        } catch (PersistenceException e) {
            hikariConnectionProvider.rollback();
            LOGGER.info(e.getMessage());
        } catch (SQLException e) {
            hikariConnectionProvider.rollback();
            LOGGER.error("Error CompanyService : delete : ", e);
            throw new ServiceException("Erreur lors de la suppression de la companie");
        }
    }

    @Override
    public List<Company> getAll() {
        List<Company> allCompanies = null;
        try {
            allCompanies = companyDao.getAll();
        } catch (PersistenceException e) {
            LOGGER.info(e.getMessage());
        }
        return allCompanies;
    }
}