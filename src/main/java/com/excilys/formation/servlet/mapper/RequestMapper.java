package com.excilys.formation.servlet.mapper;

import javax.servlet.http.HttpServletRequest;
import com.excilys.formation.dto.ComputerDto;

public class RequestMapper {
    /**
     * Fills a computerDto from the parameters of a request
     * @param pRequest the HttpServletRequest containing the parameters
     * @return a ComputerDto
     */
    public static ComputerDto toComputerDto (HttpServletRequest pRequest) {
        ComputerDto computerDto = new ComputerDto();
        String id = pRequest.getParameter("computerId").trim();
        String name = pRequest.getParameter("computerName").trim();
        String introduced = pRequest.getParameter("introduced").trim();
        String discontinued = pRequest.getParameter("discontinued").trim();
        String companyId = pRequest.getParameter("companyId").trim();
        if (!id.isEmpty()) {
           computerDto.id = Integer.parseInt(id); 
        }
        if (!name.isEmpty()) {
            computerDto.name = name;
        }
        if (!introduced.isEmpty()) {
            computerDto.introduced = introduced;
        }
        if (!discontinued.isEmpty()) {
            computerDto.discontinued = discontinued;
        }
        if (!companyId.isEmpty()) {
            int idCompany = Integer.parseInt(companyId);
            if (idCompany != 0) {
                computerDto.companyId = idCompany;
            }
        }        
        return computerDto;   
    }
}