package com.excilys.formation.service;

import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.CompanyDaoJdbc;

/**
 * Manages Company services
 * @author kfuster
 *
 */
public class CompanyServiceImpl implements CompanyService {
    private CompanyDao companyDao;
    
    public CompanyServiceImpl() {
        companyDao = CompanyDaoJdbc.getInstance();
    }
        
    public void getPage(Page<Company> pPage) {
        companyDao.getPage(pPage);
    }
}
