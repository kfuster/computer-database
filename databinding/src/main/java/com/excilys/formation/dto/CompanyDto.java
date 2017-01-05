package com.excilys.formation.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO class for companies.
 *
 * @author kfuster
 */
public class CompanyDto implements Serializable {
    private static final long serialVersionUID = -5241544729421154790L;
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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

    public static class CompanyDtoBuilder {
        private Long id;
        private String name;

        /**
         * CompanyDtoBuilder's constructor.
         *
         * @param pName the company's name
         */
        public CompanyDtoBuilder(String pName) {
            name = pName;
        }

        /**
         * Set the CompanyDtoBuilder's id.
         *
         * @param pId the Company's Id
         * @return the CompanyDtoBuilder
         */
        public CompanyDtoBuilder id(Long pId) {
            id = pId;
            return this;
        }

        /**
         * Build a CompanyDto from the builder's attributes.
         *
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
