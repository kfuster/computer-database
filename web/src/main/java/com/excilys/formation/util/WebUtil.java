package com.excilys.formation.util;

import com.excilys.formation.model.util.PageFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class that contains mapping methods transforming request into different objects.
 * @author kfuster
 */
public class WebUtil {

    /**
     * Fills a PageFilter from the parameters of a request.
     * @param parameters a map containing the parameters.
     * @return a PageFilter.
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
     * @param selection the String of ids.
     * @return a List<Long> of ids.
     */
    public static List<Long> toListIds(String selection) {
        List<Long> listIds = new ArrayList<>();
        String[] ids = selection.split(",");
        if (ids.length > 0) {
            for (String id : ids) {
                listIds.add(Long.parseLong(id.trim()));
            }
        }
        return listIds;
    }
}