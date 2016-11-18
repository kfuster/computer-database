package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.CompanyDaoJdbc;
import com.excilys.formation.service.util.ServiceUtil;

/**
 * Manages Company services.
 * @author kfuster
 *
 */
public class CompanyServiceImpl implements CompanyService {
    private CompanyDao companyDao;
    private static CompanyServiceImpl companyService;
    /**
     * Constructor for CompanyServiceImpl.
     * Initializes the companyDao.
     */
    private CompanyServiceImpl(){
        companyDao = CompanyDaoJdbc.getInstance();
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
    public Page<CompanyDto> getPage(Page<CompanyDto> pPage) {
        Page<Company> pageCompany = new Page<Company>(10);
        ServiceUtil.copyAttributes(pPage, pageCompany);
        pageCompany.elems = dtoListToCompanyList(pPage.elems);
        try {
            pageCompany = companyDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, pPage);
        pPage.elems = companyListToDtoList(pageCompany.elems);
        return pPage;
    }
    /**
     * Converts a list from CompanyDto to Company.
     * @param pListDto the list to convert
     * @return a Company List
     */
    private List<Company> dtoListToCompanyList(List<CompanyDto> pListDto) {
        List<Company> companies = null;
        if (pListDto != null) {
            companies = new ArrayList<>();
            for (CompanyDto company : pListDto) {
                companies.add(new Company.CompanyBuilder(company.name).setId(company.id).build());
            }
        }
        return companies;
    }
    /**
     * Convert a list from Company to CompanyDto.
     * @param pList the list to convert
     * @return a CompanyDto List
     */
    private List<CompanyDto> companyListToDtoList(List<Company> pList) {
        List<CompanyDto> companiesDto = null;
        if (pList != null) {
            companiesDto = new ArrayList<>();
            for (Company company : pList) {
                CompanyDto companyDto = new CompanyDto();
                companyDto.id = company.getId();
                companyDto.name = company.getName();
                companiesDto.add(companyDto);
            }
        }
        return companiesDto;
    }
    @Override
    public List<CompanyDto> getAll() {
        List<CompanyDto> allCompaniesDto = null;
        try {
            allCompaniesDto = companyListToDtoList(companyDao.getAll());
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return allCompaniesDto;
    }
}