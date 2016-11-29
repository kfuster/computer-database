package com.excilys.formation.mapper;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.pagination.Page;

/**
 * Change a Page type.
 * @author kfuster
 *
 */
public class PageMapper {
    /**
     * Converts a Page from Company to CompanyDto.
     * @param pPageCompany the Page to convert
     * @return a Page<CompanyDto>
     */
    public static Page<CompanyDto> fromCompanyToCompanyDto(Page<Company> pPageCompany) {
        Page<CompanyDto> pPageCompanyDto = null;
        if (pPageCompany != null) {
            pPageCompanyDto = new Page<>(10);
            copyAttributes(pPageCompany, pPageCompanyDto);
            pPageCompanyDto.setElements(DtoMapper.fromCompanyList(pPageCompany.getElements()));
        }
        return pPageCompanyDto;
    }

    /**
     * Converts a Page from Computer to ComputerDto.
     * @param pPageComputer the Page to convert
     * @return a Page<ComputerDto>
     */
    public static Page<ComputerDto> fromComputerToComputerDto(Page<Computer> pPageComputer) {
        Page<ComputerDto> pPageComputerDto = null;
        if (pPageComputer != null) {
            pPageComputerDto = new Page<>(10);
            copyAttributes(pPageComputer, pPageComputerDto);
            pPageComputerDto.setElements(DtoMapper.fromComputerList(pPageComputer.getElements()));
        }
        return pPageComputerDto;
    }

    /**
     * Converts a Page from ComputerDto to Computer.
     * @param pPageComputerDto the Page to convert
     * @return a Page<Computer>
     */
    public static Page<Computer> fromComputerDtoToComputer(Page<ComputerDto> pPageComputerDto) {
        Page<Computer> pPageComputer = null;
        if (pPageComputerDto != null) {
            pPageComputer = new Page<>(10);
            copyAttributes(pPageComputerDto, pPageComputer);
            pPageComputerDto.setElements(DtoMapper.fromComputerList(pPageComputer.getElements()));
        }
        return pPageComputer;
    }

    /**
     * Copy attributes from to page to another, used when we need the same page
     * but with a different list of elements (like going from a DTO page to an
     * object page).
     * @param pPageToCopy the page we want the attributes of
     * @param pNewPage the page that'll receives the attribute
     */
    public static void copyAttributes(Page<?> pPageToCopy, Page<?> pNewPage) {
        pNewPage.setPage(pPageToCopy.getPage());
        pNewPage.setElementsByPage(pPageToCopy.getElementsByPage());
        pNewPage.setTotalPages(pPageToCopy.getTotalPages());
        pNewPage.setTotalElements(pPageToCopy.getTotalElements());
    }
}