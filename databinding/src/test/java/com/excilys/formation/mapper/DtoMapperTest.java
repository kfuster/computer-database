package com.excilys.formation.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class DtoMapperTest {
    private Computer computer;
    private Computer computer_two;
    private ComputerDto computerDto;
    private ComputerDto computerDto_two;
    private Company company;
    private CompanyDto companyDto;
    private List<Computer> computers;
    private List<ComputerDto> computersDto;
    
    @Before
    public void setUp() throws Exception {
        computers = new ArrayList<>();
        computersDto = new ArrayList<>();
        company = new Company.CompanyBuilder("company").id((long) 2).build();
        companyDto = new CompanyDto.CompanyDtoBuilder("companyDto").id((long)3).build();
        computer = new Computer.ComputerBuilder("computer").id((long)1).dateDisc(LocalDate.parse("1991-04-02")).dateIntro(LocalDate.parse("1990-02-02")).company(company).build();
        computer_two = new Computer.ComputerBuilder("computer").id((long)2).dateDisc(LocalDate.parse("1991-04-02")).dateIntro(LocalDate.parse("1990-02-02")).company(company).build();
        computerDto = new ComputerDto.ComputerDtoBuilder("computer").id((long)3).discontinued("04-02-1991").introduced("05-02-1990").companyName("companyDto").companyId((long)3).build();
        computerDto_two = new ComputerDto.ComputerDtoBuilder("computer").id((long)4).discontinued("04-02-1991").introduced("05-02-1990").companyName("companyDto").companyId((long)3).build();
        computers.add(computer);
        computers.add(computer_two);
        computersDto.add(computerDto);
        computersDto.add(computerDto_two);
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
    public void testFromComputerDtoListToComputerList_ShouldReturnComputerList() {
        List<Computer> computers = new DtoMapper().toComputerList(computersDto);
        List<Computer> computersNull = new DtoMapper().toComputerList(null);
        assertNotNull(computer);
        assertEquals(computers.size(), 2);
        assertTrue(computers.get(1).getId() == 4);
        assertNull(computersNull);
    }

    @Test
    public void testFromComputerToComputerDto_ShouldReturnComputerDto() {
        ComputerDto computerDto = new DtoMapper().fromComputer(computer);
        assertNotNull(computerDto);
        assertEquals(computerDto.getName(), computer.getName());
    }

    @Test
    public void testFromComputerListToComputerDtoList_ShouldReturnComputerDtoList() {
        List<ComputerDto> computersDto = new DtoMapper().fromComputerList(computers);
        List<ComputerDto> computersDtoNull = new DtoMapper().fromComputerList(null);
        assertNotNull(computersDto);
        assertEquals(computersDto.size(), 2);
        assertTrue(computersDto.get(1).getId() == 2);
        assertNull(computersDtoNull);
    }

    @Test
    public void testToCompany() {
        //fail("Not yet implemented");
    }

    @Test
    public void testToCompanyList() {
        //fail("Not yet implemented");
    }

    @Test
    public void testFromCompany() {
        //fail("Not yet implemented");
    }

    @Test
    public void testFromCompanyList() {
        //fail("Not yet implemented");
    }
}
