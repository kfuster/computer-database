package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.ComputerDaoJdbc;
import com.excilys.formation.service.util.ServiceUtil;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
public class ComputerServiceImpl implements ComputerService {
    private ComputerDao computerDao;
    /**
     * Constructor for ComputerServiceImpl.
     * Initializes computerDao.
     */
    public ComputerServiceImpl() {
        computerDao = ComputerDaoJdbc.getInstance();
    }
    @Override
    public ComputerDto create(ComputerDto pComputerDto) {
        try {
            Computer computer = new Computer.ComputerBuilder(pComputerDto.name).setDateDisc(pComputerDto.discontinued)
                    .setDateIntro(pComputerDto.introduced)
                    .setCompany(
                            new Company.CompanyBuilder(pComputerDto.companyName).setId(pComputerDto.companyId).build())
                    .build();
            computerDao.create(computer);
            pComputerDto.id = computer.getId();
            return pComputerDto;
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void delete(int pId) {
        try {
            computerDao.delete(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ComputerDto getById(int pId) {
        Computer computer = null;
        try {
            computer = computerDao.getById(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.id = computer.getId();
            computerDto.name = computer.getName();
            computerDto.introduced = computer.getIntroduced();
            computerDto.discontinued = computer.getDiscontinued();
            Company company = computer.getCompany();
            computerDto.companyId = company.getId();
            computerDto.companyName = company.getName();
        }
        return computerDto;
    }
    @Override
    public Page<ComputerDto> getPage(Page<ComputerDto> pPage) {
        Page<Computer> pageCompany = new Page<Computer>(10);
        ServiceUtil.copyAttributes(pPage, pageCompany);
        pageCompany.elems = dtoListToComputerList(pPage.elems);
        try {
            pageCompany = computerDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, pPage);
        pPage.elems = computerListToDtoList(pageCompany.elems);
        return pPage;
    }
    @Override
    public void update(ComputerDto pComputerDto) {
        Computer computer = new Computer.ComputerBuilder(pComputerDto.name).setDateDisc(pComputerDto.discontinued)
                .setDateIntro(pComputerDto.introduced).setId(pComputerDto.id)
                .setCompany(new Company.CompanyBuilder(pComputerDto.companyName).setId(pComputerDto.companyId).build())
                .build();
        try {
            computerDao.update(computer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
    /**
     * Converts a list from ComputerDto to Computer.
     * @param pListDto the list to convert
     * @return a Computer List
     */
    private List<Computer> dtoListToComputerList(List<ComputerDto> pListDto) {
        List<Computer> computers = null;
        if (pListDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : pListDto) {
                Company company = new Company.CompanyBuilder(computer.companyName).setId(computer.companyId).build();
                computers.add(new Computer.ComputerBuilder(computer.name).setCompany(company)
                        .setDateIntro(computer.introduced).setDateDisc(computer.discontinued).setId(computer.id)
                        .build());
            }
        }
        return computers;
    }
    /**
     * Converts a list from Computer to ComputerDto.
     * @param pList the list to convert
     * @return a ComputerDto List
     */
    private List<ComputerDto> computerListToDtoList(List<Computer> pList) {
        List<ComputerDto> computersDto = null;
        if (pList != null) {
            computersDto = new ArrayList<>();
            for (Computer computer : pList) {
                ComputerDto computerDto = new ComputerDto();
                computerDto.id = computer.getId();
                computerDto.name = computer.getName();
                computerDto.introduced = computer.getIntroduced();
                computerDto.discontinued = computer.getDiscontinued();
                Company company = computer.getCompany();
                computerDto.companyId = company.getId();
                computerDto.companyName = company.getName();
                computersDto.add(computerDto);
            }
        }
        return computersDto;
    }
}
