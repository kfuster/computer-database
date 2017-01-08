package com.excilys.formation.mapper;

import com.excilys.formation.config.ServiceSpringTestConfig;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Ookami on 07/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceSpringTestConfig.class})
public class PageMapperTest {
    @Autowired
    private PageMapper pageMapper;

    @Test
    public void fromCompanyToCompanyDto() throws Exception {
        Company testCompany = new Company.CompanyBuilder("test company").build();
        Page<Company> companies = new Page<>();
        companies.setElementsByPage(10);
        companies.setPage(1);
        companies.setElements(Collections.singletonList(testCompany));
        Page<CompanyDto> companiesDto = PageMapper.fromCompanyToCompanyDto(companies);
        assertNotNull(companiesDto);
    }

    @Test
    public void fromComputerToComputerDto() throws Exception {
        Computer testComputer = new Computer.ComputerBuilder("test computer").build();
        Page<Computer> computers = new Page<>();
        computers.setElementsByPage(10);
        computers.setPage(1);
        computers.setElements(Collections.singletonList(testComputer));
        Page<ComputerDto> computersDto = pageMapper.fromComputerToComputerDto(computers);
        assertNotNull(computersDto);
    }

    @Test
    public void copyAttributes() throws Exception {
        Page<Company> companies = new Page<>();
        companies.setElementsByPage(20);
        companies.setPage(3);
        Page<Company> companiesPage = new Page<>();
        pageMapper.copyAttributes(companies, companiesPage);
        assertNotNull(companiesPage);
        assertEquals(companies.getPage(), companiesPage.getPage());
        assertEquals(companies.getElementsByPage(), companiesPage.getElementsByPage());
    }

}