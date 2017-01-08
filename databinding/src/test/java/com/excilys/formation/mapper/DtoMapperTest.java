package com.excilys.formation.mapper;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DtoMapperTest {
    private Computer computer;
    private Computer computer_two;
    private ComputerDto computerDto;
    private ComputerDto computerDto_two;
    private Company company;
    private CompanyDto companyDto;
    private List<Computer> computers;
    private List<Company> companies;
    private List<ComputerDto> computersDto;
    
    @Before
    public void setUp() throws Exception {
        computers = new ArrayList<>();
        computersDto = new ArrayList<>();
        companies = new ArrayList<>();
        company = new Company.CompanyBuilder("company").id((long) 2).build();
        Company companyTwo =new Company.CompanyBuilder("company 2").id((long) 7).build();
        companyDto = new CompanyDto.CompanyDtoBuilder("companyDto").id((long)3).build();
        computer = new Computer.ComputerBuilder("computer").id((long)1).dateDisc(LocalDate.parse("1991-04-02")).dateIntro(LocalDate.parse("1990-02-02")).company(company).build();
        computer_two = new Computer.ComputerBuilder("computer").id((long)2).dateDisc(LocalDate.parse("1991-04-02")).dateIntro(LocalDate.parse("1990-02-02")).company(company).build();
        computerDto = new ComputerDto.ComputerDtoBuilder("computer").id((long)3).discontinued("04-02-1991").introduced("05-02-1990").companyName("companyDto").companyId((long)3).build();
        computerDto_two = new ComputerDto.ComputerDtoBuilder("computer").id((long)4).discontinued("04-02-1991").introduced("05-02-1990").companyName("companyDto").companyId((long)3).build();
        computers.add(computer);
        computers.add(computer_two);
        computersDto.add(computerDto);
        computersDto.add(computerDto_two);
        companies.add(company);
        companies.add(companyTwo);
    }

    @Test
    public void testToUser() {
        //fail("Not yet implemented");
    }

    @Test
    public void testFromDtoToComputer_ShouldReturnComputer() {
        Computer computer = new DtoMapper().toComputer(computerDto);
        assertNotNull(computer);
        assertEquals(computer.getId(), computerDto.getId());
        assertEquals(computer.getName(), computerDto.getName());
    }

    @Test
    public void fromComputerDtoListToComputerList_ShouldReturnComputerList() {
        List<Computer> computers = new DtoMapper().toComputerList(computersDto);
        List<Computer> computersNull = new DtoMapper().toComputerList(null);
        assertNotNull(computer);
        assertEquals(computers.size(), 2);
        assertTrue(computers.get(1).getId() == 4);
        assertNull(computersNull);
    }

    @Test
    public void fromComputerToComputerDto_ShouldReturnComputerDto() {
        ComputerDto computerDto = new DtoMapper().fromComputer(computer);
        assertNotNull(computerDto);
        assertEquals(computerDto.getName(), computer.getName());
    }

    @Test
    public void fromComputerListToComputerDtoList_ShouldReturnComputerDtoList() {
        List<ComputerDto> computersDto = new DtoMapper().fromComputerList(computers);
        List<ComputerDto> computersDtoNull = new DtoMapper().fromComputerList(null);
        assertNotNull(computersDto);
        assertEquals(computersDto.size(), 2);
        assertTrue(computersDto.get(1).getId() == 2);
        assertNull(computersDtoNull);
    }

    @Test
    public void fromCompanyDtoToCompany_ShouldReturnCompany() {
        Company company = new DtoMapper().toCompany(companyDto);
        assertNotNull(company);
        assertEquals(company.getId(), companyDto.getId());
        assertEquals(company.getName(), companyDto.getName());
    }

    @Test
    public void fromCompanyToCompanyDto_ShouldReturnCOmpanyDto() {
        CompanyDto companyDto = new DtoMapper().fromCompany(company);
        assertNotNull(companyDto);
        assertEquals(companyDto.getName(), company.getName());
    }

    @Test
    public void fromCompanyListToCompanyDtoList_ShouldReturnCompanyDtoList() {
        List<CompanyDto> companiesDto = new DtoMapper().fromCompanyList(companies);
        List<CompanyDto> companiesDtoNull = new DtoMapper().fromCompanyList(null);
        assertNotNull(companiesDto);
        assertEquals(companiesDto.size(), 2);
        assertTrue(companiesDto.get(1).getId() == 7);
        assertNull(companiesDtoNull);
    }
}
