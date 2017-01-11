package com.excilys.formation.mapper;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Change a Page type.
 * @author kfuster
 */
@Component
public class PageMapper {

    @Autowired
    private DtoMapper dtoMapper;

    /**
     * Converts a Page from Company to CompanyDto.
     * @param pageCompany the Page to convert
     * @return a Page<CompanyDto>
     */
    public static Page<CompanyDto> fromCompanyToCompanyDto(Page<Company> pageCompany) {
        Page<CompanyDto> pPageCompanyDto = null;
        if (pageCompany != null) {
            pPageCompanyDto = new Page<>(10);
            copyAttributes(pageCompany, pPageCompanyDto);
            pPageCompanyDto.setElements(DtoMapper.fromCompanyList(pageCompany.getElements()));
        }
        return pPageCompanyDto;
    }

    /**
     * Converts a Page from Computer to ComputerDto.
     * @param pageComputer the Page to convert
     * @return a Page<ComputerDto>
     */
    public Page<ComputerDto> fromComputerToComputerDto(Page<Computer> pageComputer) {
        Page<ComputerDto> pPageComputerDto = null;
        if (pageComputer != null) {
            pPageComputerDto = new Page<>(10);
            copyAttributes(pageComputer, pPageComputerDto);
            pPageComputerDto.setElements(dtoMapper.fromComputerList(pageComputer.getElements()));
            pageComputer = null;
            System.gc();
        }
        return pPageComputerDto;
    }

    /**
     * Copy attributes from to page to another, used when we need the same page
     * but with a different list of elements (like going from a DTO page to an
     * object page).
     * @param pageToCopy the page we want the attributes of
     * @param newPage the page that'll receives the attribute
     */
    public static void copyAttributes(Page<?> pageToCopy, Page<?> newPage) {
        newPage.setPage(pageToCopy.getPage());
        newPage.setElementsByPage(pageToCopy.getElementsByPage());
        newPage.setTotalPages(pageToCopy.getTotalPages());
        newPage.setTotalElements(pageToCopy.getTotalElements());
    }
}