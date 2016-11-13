package com.excilys.formation.pagination;

import java.util.List;

/**
 * A Page object containing informations about the current page shown.
 * @author kfuster
 *
 * @param <T>
 */
public class Page<T> {
    public int elemByPage;
    public int page = 1;
    public int totalElem;
    public int nbPages;
    public List<T> elems;
    /**
     * Page constructor.
     * @param pElemByPage the number of elements by page. Used as LIMIT and to calculate OFFSET in requests
     */
    public Page(int pElemByPage) {
        elemByPage = pElemByPage;
    }
    /**
     * Increment the page number if it's not already the last page (nbPages).
     * @return a boolean indicating if the page was changed
     */
    public boolean nextPage() {
        if (page < nbPages) {
            page++;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Decrement the page number if it's already the first page (1).
     * @return a boolean indicating if the page was changed
     */
    public boolean prevPage() {
        if (page > 1) {
            page--;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Set the page number if it's within a valid interval (1 or above and the
     * number of pages or below).
     * @param page int the number of the page we want
     * @return a boolean indicating if the page was changed
     */
    public boolean setPage(int page) {
        if (page >= 1 && page <= nbPages) {
            this.page = page;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Set the total number of elements and calculates the number of pages.
     * @param totalElem the total number of elements
     */
    public void setTotalElem(int totalElem) {
        this.totalElem = totalElem;
        calculateNbPages();
    }
    /**
     * Set the number of elements by pages and calculates the number of pages.
     * @param pElemByPage the number of elements by pages
     */
    public void setElemenByPage(int pElemByPage) {
        elemByPage = pElemByPage;
        calculateNbPages();
    }
    /**
     * Calculates the number of pages with the number of elements and the number.
     * of elements by pages
     */
    public void calculateNbPages() {
        nbPages = (totalElem + elemByPage - 1) / elemByPage;
    }
}