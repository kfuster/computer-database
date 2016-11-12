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
 * Manages Company services
 * @author kfuster
 *
 */
public class CompanyServiceImpl implements CompanyService {
    private CompanyDao companyDao;
    
    public CompanyServiceImpl() {
        companyDao = CompanyDaoJdbc.getInstance();
    }
    
    @Override
    public Page<CompanyDto> getPage(Page<CompanyDto> pPage) {
    	Page<Company> pageCompany = new Page<Company>(10); 
    	ServiceUtil.copyAttributes(pPage, pageCompany); 
    	pageCompany.elems = DtoListToCompanyList(pPage.elems);
        try {
            pageCompany = companyDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, pPage);
        pPage.elems = CompanyListToDtoList(pageCompany.elems);
        return pPage;
    }
    
    /**
     * Converts a list from CompanyDto to Company
     * @param pListDto the list to convert
     * @return a Company List
     */
	private List<Company> DtoListToCompanyList(List<CompanyDto> pListDto) {
    	List<Company> companies = null;
    	if(pListDto != null) {
    		companies = new ArrayList<>();
    		for(CompanyDto company : pListDto) {
    			companies.add(new Company.CompanyBuilder(company.name).setId(company.id).build());
    		}
    	}
    	return companies;
    }
	
	/**
     * Convert a list from Company to CompanyDto
     * @param pList the list to convert
     * @return a CompanyDto List
     */
	private List<CompanyDto> CompanyListToDtoList(List<Company> pList) {
		List<CompanyDto> companiesDto = null; 
		if(pList != null) {
			companiesDto = new ArrayList<>();
			for(Company company : pList) {
				CompanyDto companyDto = new CompanyDto();
				companyDto.id = company.getId();
				companyDto.name = company.getName();
				companiesDto.add(companyDto);
			}
		}
		return companiesDto;
	}
	
	
}
