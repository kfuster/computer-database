package com.excilys.formation.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * A Page object containing informations about the current page shown.
 * @author kfuster
 * @param <T> the objects that our pages will contain.
 */
public class Page<T> implements Serializable {
    private int elementsByPage = 10;
    private int page = 1;
    private int totalElements;
    private int totalPages;
    private List<T> elements;
    /**
     * Default constructor.
     */
    public Page() {
    }

    /**
     * Page constructor which set elemByPage.
     * @param elemByPage the number of elements by page. Used as LIMIT and to
     *            calculate OFFSET in requests
     */
    public Page(int elemByPage) {
        elementsByPage = elemByPage;
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
     * @param elemByPage the number of elements by pages
     */
    public void setElementsByPage(int elemByPage) {
        elementsByPage = elemByPage;
        calculateNbPages();
    }

    /**
     * Calculates the number of pages with the number of elements and the
     * number. of elements by pages
     */
    private void calculateNbPages() {
        if (totalElements != 0 && elementsByPage != 0) {
            totalPages = (totalElements + elementsByPage - 1) / elementsByPage;
        } else {
            totalPages = 1;
        }
    }

    /**
     * Getter for the totalElements field.
     * @return int representing the number of elements.
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * Getter for the totalPages field.
     * @return int representing the number of pages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Setter for the totalPages field.
     * @param nbPages int representing the number of pages.
     */
    public void setTotalPages(int nbPages) {
        this.totalPages = nbPages;
    }

    /**
     * Getter for the elements field.
     * @return List<T> representing the objects contained in our current page.
     */
    public List<T> getElements() {
        return elements;
    }

    /**
     * Setter for the elements field.
     * @param elems List<T> representing the objects contained in our current page.
     */
    public void setElements(List<T> elems) {
        this.elements = elems;
    }

    /**
     * Getter for the elementsByPage field.
     * @return int representing the number of elements per page.
     */
    public int getElementsByPage() {
        return elementsByPage;
    }

    /**
     * Getter for the page field.
     * @return int representing the current page number.
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter for the page field.
     * @param page int representing the current page number.
     */
    public void setPage(int page) {
        this.page = page;
    }
}