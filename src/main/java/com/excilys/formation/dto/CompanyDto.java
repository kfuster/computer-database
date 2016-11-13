package com.excilys.formation.dto;

/**
 * DTO class for companies.
 * @author kfuster
 *
 */
public class CompanyDto {
    public int id;
    public String name;
    @Override
    public String toString() {
        return new StringBuilder().append("Company [id=").append(id).append(", name=").append(name).append("]")
                .toString();
    }
}
