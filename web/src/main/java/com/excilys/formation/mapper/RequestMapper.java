package com.excilys.formation.mapper;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.util.PageFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Map to request to different objects.
 *
 * @author kfuster
 */
public class RequestMapper {
    /**
     * Fills a computerDto from the parameters of a request.
     *
     * @param pRequest the HttpServletRequest containing the parameters
     * @return a ComputerDto
     */
    public static ComputerDto toComputerDto(HttpServletRequest pRequest) {
        ComputerDto computerDto = new ComputerDto();
        String id = "";
        if (pRequest.getParameter("computerId") != null) {
            id = pRequest.getParameter("computerId").trim();
        }
        String name = pRequest.getParameter("computerName").trim();
        String introduced = pRequest.getParameter("introduced").trim();
        String discontinued = pRequest.getParameter("discontinued").trim();
        String companyId = pRequest.getParameter("companyId").trim();
        if (!id.isEmpty()) {
            computerDto.setId(Long.parseLong(id));
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
            long idCompany = Long.parseLong(companyId);
            if (idCompany != 0) {
                computerDto.setCompanyId(idCompany);
            }
        }
        return computerDto;
    }

    /**
     * Fills a PageFilter from the parameters of a request.
     *
     * @param pRequest the HttpServletRequest containing the parameters
     * @return a PageFilter
     */
    public static PageFilter toPageFilter(Map<String, String> parameters) {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        if (parameters.containsKey("page")) {
            pageFilter.setPageNum(Integer.parseInt(parameters.get("page")));
        }
        if (parameters.containsKey("limit")) {
            pageFilter.setElementsByPage(Integer.parseInt(parameters.get("limit")));
        }

        if (parameters.containsKey("search")) {
            String search = parameters.get("search");
            pageFilter.addCondition("computerName", search);
            pageFilter.addCondition("companyName", search);
        }
        if (parameters.containsKey("column")) {
            String column = parameters.get("column").replace("'", "''");
            if ("computerName".equals(column)) {
                column = "name";
            }
            if ("companyName".equals(column)) {
                column = "name";
            }
            pageFilter.addCondition("column", column);
        }
        if (parameters.containsKey("order")) {
            String order = parameters.get("order").replace("'", "''");
            pageFilter.addCondition("order", order);
        }
        return pageFilter;
    }

    /**
     * Parse the String of ids to delete to a List.
     *
     * @param selection the String of ids
     * @return a List<Long> of ids
     */
    public static List<Long> toListIds(String selection) {
        List<Long> listIds = new ArrayList<>();
        String[] ids = selection.split(",");
        if (ids.length > 0) {
            for (String id : ids) {
                listIds.add(Long.parseLong(id));
            }
        }
        return listIds;
    }
}