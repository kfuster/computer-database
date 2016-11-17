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
    public int totalElement;
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
     * @param pPage int the number of the page we want
     * @return a boolean indicating if the page was changed
     */
    public boolean trySetPage(int pPage) {
        if (pPage >= 1 && pPage <= nbPages) {
            this.page = pPage;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Set the total number of elements and calculates the number of pages.
     * @param totalElem the total number of elements
     */
    public void setTotalElement(int totalElem) {
        this.totalElement = totalElem;
        calculateNbPages();
    }
    /**
     * Set the number of elements by pages and calculates the number of pages.
     * @param pElemByPage the number of elements by pages
     */
    public void setElemByPage(int pElemByPage) {
        elemByPage = pElemByPage;
        calculateNbPages();
    }
    /**
     * Calculates the number of pages with the number of elements and the number.
     * of elements by pages
     */
    private void calculateNbPages() {
        if(totalElement != 0) {
            nbPages = (totalElement + elemByPage - 1) / elemByPage;
        }
    }
    public int getTotalElement() {
        return totalElement;
    }
    public int getNbPages() {
        return nbPages;
    }
    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }
    public List<T> getElems() {
        return elems;
    }
    public void setElems(List<T> elems) {
        this.elems = elems;
    }
    public int getElemByPage() {
        return elemByPage;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
}