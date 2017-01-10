package com.excilys.formation.model;

import com.excilys.formation.converter.LocalDateConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Class representing the computer entity.
 * @author kfuster
 */
@Entity
@Table(name = "computer")
public final class Computer implements Serializable {
    private static final long serialVersionUID = -7424514145722416760L;
    // ######### ATTRIBUTES #########
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    @Convert(converter = LocalDateConverter.class)
    @JsonDeserialize(using =  LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate introduced;
    @Column
    @Convert(converter = LocalDateConverter.class)
    @JsonDeserialize(using =  LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate discontinued;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * Default constructor.
     */
    protected Computer() {
    }

    /**
     * Computer constructor, used by ComputerBuilder.
     *
     * @param pName the computer's name
     */
    private Computer(String pName) {
        name = pName;
    }

    // ######### GETTERS/SETTERS #########

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

    /**
     * Getter for the introduced field.
     * @return LocalDate representing the introduced.
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Setter for the introduced field.
     * @param introduced LocalDate representing the introduced.
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * Getter for the discontinued field.
     * @return LocalDate representing the discontinued.
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Setter for the discontinued field.
     * @param discontinued LocalDate representing the discontinued.
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Getter for the company field.
     * @return Company representing the company.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Setter for the company field.
     * @param company Company representing the company.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    // ######### METHODS #########
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = (int) (prime * result + id);
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
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
        return "Computer [id=" + id + ", name=" + name +
                ", introduced=" + introduced + ", discontinued=" + discontinued +
                ", company=" + company + "]";
    }

    /**
     * Class allowing the creation of computers.
     */
    public static class ComputerBuilder {
        private Long id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

        /**
         * ComputerBuilder constructor.
         * @param name the computer's name
         */
        public ComputerBuilder(String name) {
            this.name = name;
        }

        /**
         * Setter for the ComputerBuilder's company.
         * @param company the Computer's Company
         * @return the ComputerBuilder
         */
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's id.
         * @param id the Computer's Id
         * @return the ComputerBuilder
         */
        public ComputerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's introduced date.
         * @param date the Computer's introduced date
         * @return the ComputerBuilder
         */
        public ComputerBuilder dateIntro(LocalDate date) {
            introduced = date;
            return this;
        }

        /**
         * Setter for the ComputerBuilder's discontinued date.
         * @param date the Computer's discontinued date
         * @return the ComputerBuilder
         */
        public ComputerBuilder dateDisc(LocalDate date) {
            discontinued = date;
            return this;
        }

        /**
         * Build a Computer from the ComputerBuilder's attributes.
         * @return a Computer built with the builder's attributes.
         */
        public Computer build() {
            if (name.length() < 2) {
                throw new IllegalArgumentException("Computer name must be at least 2 characters");
            }
            Computer computer = new Computer(name);
            computer.id = id;
            computer.company = company;
            computer.discontinued = discontinued;
            computer.introduced = introduced;
            return computer;
        }
    }
}