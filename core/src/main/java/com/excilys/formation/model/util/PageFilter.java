package com.excilys.formation.model.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the different constraints a Page object might be under.
 */
public class PageFilter {
    private int nbPage;
    private int elementsByPage;
    private int pageNum;
    private Map<String, String> conditions = new HashMap<>();

    /**
     * Adds a condition in the conditions Map.
     * @param key the condition's key
     * @param value the condition's value
     */
    public void addCondition(String key, String value) {
        conditions.put(key, value);
    }

    /**
     * Getter for the number of pages contained.
     * @return int representing the number of pages.
     */
    public int getNbPage() {
        return nbPage;
    }

    /**
     * Setter for the number of pages contained.
     * @param nbPage int representing the number of pages.
     */
    public void setNbPage(int nbPage) {
        this.nbPage = nbPage;
    }

    /**
     * Getter for the number of elements per page.
     * @return int representing the number of elements per page.
     */
    public int getElementsByPage() {
        return elementsByPage;
    }

    /**
     * Setter for the number of elements per page.
     * @param elementsByPage int representing the number of elements per page.
     */
    public void setElementsByPage(int elementsByPage) {
        this.elementsByPage = elementsByPage;
    }

    /**
     * Getter for the optionnal conditions map.
     * @return Map<String,String> map of string representing the optionnals conditions.
     */
    public Map<String, String> getConditions() {
        return conditions;
    }

    /**
     * Getter for the current page.
     * @return int representing the current page number.
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * Setter for the current page.
     * @param pageNum int representing the current page number.
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}