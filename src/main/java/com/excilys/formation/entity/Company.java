package com.excilys.formation.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Company entity.
 * @author kfuster
 *
 */
public class Company {
    // ######### ATTRIBUTES #########
    private int id;
    private String name;
    private List<Computer> computers;
    /**
     * Company constructor.
     * @param pName the name of the Company.
     */
    private Company(String pName) {
        computers = new ArrayList<>();
        name = pName;
    }
    // ######### SETTERS/GETTERS #########
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Computer> getComputers() {
        return computers;
    }
    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }
    // ######### METHODS #########
    /**
     * Method to add a Computer to the computer list of the company. UNUSED
     * @param pComputer the computer to add
     */
    public void addComputer(Computer pComputer) {
        computers.add(pComputer);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return new StringBuilder().append("Company [id=").append(id).append(", name=").append(name).append("]")
                .toString();
    }
    public static class CompanyBuilder {
        private int id;
        private String name;
        /**
         * CompanyBuilder constructor.
         * @param pName the company's name
         */
        public CompanyBuilder(String pName) {
            name = pName;
        }
        /**
         * Set the CompanyBuilder's id.
         * @param pId the Company's Id
         * @return the CompanyBuilder
         */
        public CompanyBuilder setId(int pId) {
            id = pId;
            return this;
        }
        /**
         * Build a company from the builder's attributes.
         * @return a Company
         */
        public Company build() {
            Company company = new Company(name);
            company.id = id;
            return company;
        }
    }
}