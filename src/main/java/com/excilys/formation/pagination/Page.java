package com.excilys.formation.pagination;

import java.util.List;

/**
 * A Page object containing informations about the current page shown.
 * @author kfuster
 *
 * @param <T>
 */
public class Page<T> {
    public int elementsByPage = 10;
    public int page = 1;
    public int totalElements;
    public int totalPages;
    public List<T> elements;
    public Page() {
    }
    /**
     * Page constructor which set elemByPage.
     * @param pElemByPage the number of elements by page. Used as LIMIT and to calculate OFFSET in requests
     */
    public Page(int pElemByPage) {
        elementsByPage = pElemByPage;
    }
    /**
     * Set the total number of elements and calculates the number of pages.
     * @param totalElem the total number of elements
     */
    public void setTotalElements(int totalElem) {
        this.totalElements = totalElem;
        calculateNbPages();
    }
    /**
     * Set the number of elements by pages and calculates the number of pages.
     * @param pElemByPage the number of elements by pages
     */
    public void setElementsByPage(int pElemByPage) {
        elementsByPage = pElemByPage;
        calculateNbPages();
    }
    /**
     * Calculates the number of pages with the number of elements and the number.
     * of elements by pages
     */
    private void calculateNbPages() {
        if(totalElements != 0 && elementsByPage != 0) {
            totalPages = (totalElements + elementsByPage - 1) / elementsByPage;
        }
        else {
            totalPages = 1;
        }
    }
    public int getTotalElements() {
        return totalElements;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int nbPages) {
        this.totalPages = nbPages;
    }
    public List<T> getElements() {
        return elements;
    }
    public void setElements(List<T> elems) {
        this.elements = elems;
    }
    public int getElementsByPage() {
        return elementsByPage;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
}