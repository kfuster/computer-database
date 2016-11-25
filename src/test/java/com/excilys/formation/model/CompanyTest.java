package com.excilys.formation.model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.excilys.formation.model.Company;

public class CompanyTest {
    private Company company;
    @Before
    public void setUp() throws Exception {
        company = new Company.CompanyBuilder("Company test").id(150).build();
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testHashCode() {
        Company newCompany = new Company.CompanyBuilder("Company test").id(150).build();
        assertTrue("Hashcodes equals", company.hashCode() == newCompany.hashCode());
        newCompany.setName("New name");
        assertTrue("Hashcodes : different names", company.hashCode() != newCompany.hashCode());
    }
    @Test
    public void testGetId() {
        assertTrue("Get id", company.getId() == 150);
    }
    @Test
    public void testSetId() {
        company.setId(45);
        assertTrue("Set id", company.getId() == 45);
    }
    @Test
    public void testGetName() {
        assertEquals("Get name", company.getName(), "Company test");
    }
    @Test
    public void testSetName() {
        company.setName("New name");
        assertEquals("Set name", company.getName(), "New name");
    }
    @Test
    public void testEqualsObject() {
        Company newCompany = new Company.CompanyBuilder("Company test").id(150).build();
        assertEquals("Equals", company, newCompany);
    }
}