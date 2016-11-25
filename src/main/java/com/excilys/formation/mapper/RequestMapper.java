package com.excilys.formation.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.util.MenuUtil;

public class RequestMapper {
    /**
     * Fills a computerDto from the parameters of a request
     * @param pRequest the HttpServletRequest containing the parameters
     * @return a ComputerDto
     */
    public static ComputerDto toComputerDto (HttpServletRequest pRequest) {
        ComputerDto computerDto = new ComputerDto();
        String id = "";
        if (pRequest.getParameter("computerId") != null) {
            id = pRequest.getParameter("computerId").trim();
        }
        String name = pRequest.getParameter("computerName").trim();
        String introduced = pRequest.getParameter("introduced").trim();
        String discontinued = pRequest.getParameter("discontinued").trim();
        String companyId = pRequest.getParameter("companyId").trim();
        if (!id.isEmpty() && MenuUtil.isInteger(id)) {
           computerDto.setId(Integer.parseInt(id)); 
        }
        if (!name.isEmpty()) {
            computerDto.setName(name);
        }
        if (!introduced.isEmpty()) {
            computerDto.setIntroduced(introduced);
        }
        if (!discontinued.isEmpty()) {
            computerDto.setDiscontinued(discontinued);
        }
        if (!companyId.isEmpty()) {
            int idCompany = Integer.parseInt(companyId);
            if (idCompany != 0) {
                computerDto.setCompanyId(idCompany);
            }
        }        
        return computerDto;   
    }
    /**
     * Get a list of ids from a request
     * @param pRequest the HttpServletRequest containing the list of ids
     * @return a List of ids
     */
    public static List<Integer> toIdList (HttpServletRequest pRequest) {
        List<Integer> listIds = new ArrayList<>();
        String[] stringIdArray = pRequest.getParameter("selection").trim().split(",");
        for (String id : stringIdArray) {
            if (MenuUtil.isInteger(id)) {
                listIds.add(Integer.parseInt(id));
            }
        }
        return listIds;
    }
}