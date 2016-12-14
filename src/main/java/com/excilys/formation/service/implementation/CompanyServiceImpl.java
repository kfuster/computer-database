package com.excilys.formation.service.implementation;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.CompanyService;
import com.zaxxer.hikari.HikariDataSource;
import ch.qos.logback.classic.Logger;

/**
 * Manages Company services.
 * @author kfuster
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;
    @Autowired
    private DataSource dataSource;

    public CompanyServiceImpl() {
    }

    public void setComputerDao(ComputerDao pComputerDao) {
        computerDao = pComputerDao;
    }

    public void setCompanyDao(CompanyDao pCompanyDao) {
        companyDao = pCompanyDao;
    }

    public void setDataSource(HikariDataSource pDataSource) {
        dataSource = pDataSource;
    }

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
            Connection connection = DataSourceUtils.getConnection(dataSource);
            computerDao.deleteByCompany(connection, pId);
            companyDao.delete(connection, pId);
        } catch (PersistenceException e) {
            LOGGER.error( "CompanyServiceImpl : delete() catched Exception",e);
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