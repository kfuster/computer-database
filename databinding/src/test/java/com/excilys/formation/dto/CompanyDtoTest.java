package com.excilys.formation.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ookami on 08/01/2017.
 */
public class CompanyDtoTest {

    @Test
    public void companyDtoHashCode() throws Exception {
        CompanyDto companyDtoOne = new CompanyDto.CompanyDtoBuilder("company 1").id((long)1).build();
        CompanyDto companyDtoTwo = new CompanyDto.CompanyDtoBuilder("company 1").id((long)1).build();
        assertEquals(companyDtoOne.hashCode(), companyDtoTwo.hashCode());
    }

    @Test
    public void CompanyDtoEquals() throws Exception {
        CompanyDto companyDtoOne = new CompanyDto.CompanyDtoBuilder("company 1").id((long)1).build();
        CompanyDto companyDtoTwo = new CompanyDto.CompanyDtoBuilder("company 1").id((long)1).build();
        assertEquals(companyDtoOne, companyDtoTwo);
    }
}