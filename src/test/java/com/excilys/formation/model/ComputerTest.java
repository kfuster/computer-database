package com.excilys.formation.model;

import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerTest {
    private Computer computer;
    @Before
    public void setUp() throws Exception {
        computer = new Computer.ComputerBuilder("Test computer").id(2).dateIntro(LocalDate.parse("1991-05-24"))
                .dateDisc(LocalDate.parse("1992-04-21"))
                .company(new Company.CompanyBuilder("Company test").id(3).build()).build();
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testHashCode() {
        Computer computerTest = new Computer.ComputerBuilder("Test computer").id(2)
                .dateIntro(LocalDate.parse("1991-05-24")).dateDisc(LocalDate.parse("1992-04-21"))
                .company(new Company.CompanyBuilder("Company test").id(3).build()).build();
        assertTrue("Hashcodes equals", computer.hashCode() == computerTest.hashCode());
        computerTest.setName("Different name");
        assertTrue("Hashcodes : different names", computer.hashCode() != computerTest.hashCode());
    }
    @Test
    public void testGetId() {
        assertTrue("Get id equals", computer.getId() == 2);
    }
    @Test
    public void testSetId() {
        computer.setId(150);
        assertTrue("Set id", computer.getId() == 150);
    }
    @Test
    public void testGetName() {
        assertEquals("Get name equals", computer.getName(), "Test computer");
    }
    @Test
    public void testSetName() {
        computer.setName("New name");
        assertEquals("Set name", computer.getName(), "New name");
    }
    @Test
    public void testGetIntroduced() {
        assertEquals("Get introduced", computer.getIntroduced(), LocalDate.parse("1991-05-24"));
    }
    @Test
    public void testSetIntroduced() {
        computer.setIntroduced(LocalDate.parse("1987-03-14"));
        assertEquals("Set introduced", computer.getIntroduced(), LocalDate.parse("1987-03-14"));
    }
    @Test
    public void testGetDiscontinued() {
        assertEquals("Get discontinued", computer.getDiscontinued(), LocalDate.parse("1992-04-21"));
    }
    @Test
    public void testSetDiscontinued() {
        computer.setDiscontinued(LocalDate.parse("1989-04-17"));
        assertEquals("Set discontinued", computer.getDiscontinued(), LocalDate.parse("1989-04-17"));
    }
    @Test
    public void testGetCompany() {
        Company company = new Company.CompanyBuilder("Company test").id(3).build();
        assertEquals("Get company", computer.getCompany(), company);
    }
    @Test
    public void testSetCompany() {
        Company company = new Company.CompanyBuilder("New company").id(310).build();
        computer.setCompany(company);
        assertEquals("Set company", computer.getCompany(), company);
    }
    @Test
    public void testEqualsObject() {
        Computer computerTest = new Computer.ComputerBuilder("Test computer").id(2)
                .dateIntro(LocalDate.parse("1991-05-24")).dateDisc(LocalDate.parse("1992-04-21"))
                .company(new Company.CompanyBuilder("Company test").id(3).build()).build();
        assertEquals("Equals", computer, computerTest);
    }
}