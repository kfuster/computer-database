package com.excilys.formation.service.implementation;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Manages Company services.
 * @author kfuster
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ComputerDao computerDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Company> getPage(PageFilter pageFilter) {
        return companyDao.getPage(pageFilter);
    }

    @Override
    @Transactional
    @CacheEvict(value = "cacheCompanies", allEntries = true)
    public void delete(long id) {
        computerDao.deleteByCompany(id);
        companyDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("cacheCompanies")
    public List<Company> getAll() {
        List<Company> allCompanies = null;
        allCompanies = companyDao.getAll();
        return allCompanies;
    }

    @Override
    @Transactional(readOnly = true)
    public Company getById(long id) {
        return companyDao.getById(id);
    }
}