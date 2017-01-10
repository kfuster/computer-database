package com.excilys.formation.mapper;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Computer.ComputerBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Mapper class for DTOs.
 * @author kfuster
 */
@Component
public class DtoMapper {

    /**
     * Converts a ComputerDto to a Computer.
     * @param computerDto the ComputerDto to convert
     * @return a Computer
     */
    public Computer toComputer(ComputerDto computerDto) {
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", LocaleContextHolder.getLocale());
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(messages.getString("util.date.format"));
        Computer computer = null;
        if (computerDto != null) {
            Company company = new Company.CompanyBuilder(computerDto.getCompanyName()).id(computerDto.getCompanyId())
                    .build();
            ComputerBuilder builder = new Computer.ComputerBuilder(computerDto.getName()).id(computerDto.getId())
                    .company(company);
            if (computerDto.getIntroduced() != null && !computerDto.getIntroduced().isEmpty()) {
                builder.dateIntro(LocalDate.parse(computerDto.getIntroduced(), formatter));
            }
            if (computerDto.getDiscontinued() != null && !computerDto.getDiscontinued().isEmpty()) {
                builder.dateDisc(LocalDate.parse(computerDto.getDiscontinued(), formatter)).build();
            }
            computer = builder.build();
        }
        return computer;
    }

    /**
     * Converts a List of ComputerDtos to a List of Computer.
     * @param listComputerDto the List of ComputerDto to convert
     * @return a List of Computers
     */
    public List<Computer> toComputerList(List<ComputerDto> listComputerDto) {
        if (listComputerDto != null) {
            List<Computer> computers = new ArrayList<>();
            listComputerDto.forEach(computer -> computers.add(toComputer(computer)));
            return computers;
        } else {
            return null;
        }
    }

    /**
     * Converts a Computer to a ComputerDto.
     * @param computer the Computer to convert
     * @return a ComputerDto
     */
    public ComputerDto fromComputer(Computer computer) {
        ResourceBundle messages = ResourceBundle.getBundle("messages/messages", LocaleContextHolder.getLocale());
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(messages.getString("util.date.format"));
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(computer.getId());
            computerDto.setName(computer.getName());
            LocalDate dateIntro = computer.getIntroduced();
            if (dateIntro != null) {
                computerDto.setIntroduced(dateIntro.format(formatter));
            }
            LocalDate dateDisc = computer.getDiscontinued();
            if (dateDisc != null) {
                computerDto.setDiscontinued(dateDisc.format(formatter));
            }
            if (computer.getCompany() != null) {
                Company company = computer.getCompany();
                computerDto.setCompanyId(company.getId());
                computerDto.setCompanyName(company.getName());
            }
        }
        return computerDto;
    }

    /**
     * Converts a List of Computers to a List of ComputerDto.
     * @param listComputers the List of Computers to convert
     * @return a List of ComputerDto
     */
    public List<ComputerDto> fromComputerList(List<Computer> listComputers) {
        if (listComputers != null) {
            List<ComputerDto> computersDto = new ArrayList<>();
            listComputers.forEach(computer -> computersDto.add(fromComputer(computer)));
            return computersDto;
        } else {
            return null;
        }
    }

    /**
     * Converts a CompanyDto to a Company.
     * @param companyDto the CompanyDto to convert
     * @return a Company
     */
    public static Company toCompany(CompanyDto companyDto) {
        return new Company.CompanyBuilder(companyDto.getName()).id(companyDto.getId()).build();
    }

    /**
     * Converts a Company to a CompanyDto.
     * @param company the Company to convert
     * @return a CompanyDto
     */
    public static CompanyDto fromCompany(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        return companyDto;
    }

    /**
     * Converts a List of Company to a List of CompanyDto.
     * @param listCompanies the List of Company to convert
     * @return a List of CompanyDto
     */
    public static List<CompanyDto> fromCompanyList(List<Company> listCompanies) {
        if (listCompanies != null) {
            List<CompanyDto> companiesDto = new ArrayList<>();
            listCompanies.forEach(company -> companiesDto.add(fromCompany(company)));
            return companiesDto;
        } else {
            return null;
        }
    }
}