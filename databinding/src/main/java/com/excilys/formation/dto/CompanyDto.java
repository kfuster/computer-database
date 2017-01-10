package com.excilys.formation.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO class for companies.
 * @author kfuster
 */
public class CompanyDto implements Serializable {
    private static final long serialVersionUID = -5241544729421154790L;
    private Long id;
    private String name;

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
     * @return String representing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name field.
     * @param name String representing the name.
     */
    public void setName(String name) {
        this.name = name;
    }

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
        CompanyDto other = (CompanyDto) obj;
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
     * Class allowing the construction of CompanyDto.
     */
    public static class CompanyDtoBuilder {
        private Long id;
        private String name;

        /**
         * CompanyDtoBuilder's constructor.
         * @param name the company's name
         */
        public CompanyDtoBuilder(String name) {
            this.name = name;
        }

        /**
         * Set the CompanyDtoBuilder's id.
         * @param id the Company's Id
         * @return the CompanyDtoBuilder
         */
        public CompanyDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Build a CompanyDto from the builder's attributes.
         * @return a CompanyDto
         */
        public CompanyDto build() {
            CompanyDto companyDto = new CompanyDto();
            companyDto.name = name;
            companyDto.id = id;
            return companyDto;
        }
    }
}
