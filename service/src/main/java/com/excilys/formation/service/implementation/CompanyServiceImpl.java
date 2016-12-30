package com.excilys.formation.service.implementation;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.CompanyService;
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

    public void setComputerDao(ComputerDao pComputerDao) {
        computerDao = pComputerDao;
    }

    public void setCompanyDao(CompanyDao pCompanyDao) {
        companyDao = pCompanyDao;
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

    @Transactional
    @Override
    public void delete(long pId) throws ServiceException {
        try {
            computerDao.deleteByCompany(pId);
            companyDao.delete(pId);
        } catch (PersistenceException e) {
            LOGGER.error("CompanyServiceImpl : delete() catched PersistenceException", e);
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