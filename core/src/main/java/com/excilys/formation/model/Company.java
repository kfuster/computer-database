package com.excilys.formation.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing a company entity.
 * Typically a company manufactured one or several computers.
 * @author kfuster
 */
@Entity
@Table(name = "company")
public final class Company implements Serializable {
    private static final long serialVersionUID = 2065269485702193829L;
    // ######### ATTRIBUTES #########
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;

    /**
     * Default constructor.
     */
    protected Company() {
    }

    /**
     * Company constructor.
     * @param pName the name of the Company.
     */
    private Company(String pName) {
        name = pName;
    }

    // ######### SETTERS/GETTERS #########

    /**
     * Getter for the id field.
     * @return Long representing the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id field.
     * @param id Long representing the id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the name field.
     * @return String representing the name;
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name field.
     * @param name String representing the name;
     */
    public void setName(String name) {
        this.name = name;
    }

    // ######### METHODS #########
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + id);
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
        if (!Objects.equals(id, other.id)) {
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
        return "Company [id=" + id + ", name=" + name + "]";
    }

    /**
     * Class allowing the creation of companies.
     */
    public static class CompanyBuilder {
        private Long id;
        private String name;

        /**
         * CompanyBuilder constructor.
         * @param name the company's name
         */
        public CompanyBuilder(String name) {
            this.name = name;
        }

        /**
         * Setter for the CompanyBuilder's id.
         * @param id the Company's Id
         * @return the CompanyBuilder
         */
        public CompanyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Build a company from the builder's attributes.
         * @return a Company built with the builder's values.
         */
        public Company build() {
            Company company = new Company(name);
            company.id = id;
            return company;
        }
    }
}