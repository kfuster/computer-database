package com.excilys.formation.pagination;

import java.util.List;

public class Page<T> {
    public int elemByPage;
    public int page = 1;
    public int totalElem;
    public int nbPages;
    public List<T> elems;

    public Page(int pElemByPage) {
        elemByPage = pElemByPage;
    }

    public boolean nextPage() {
        if (page < nbPages) {
            page++;
            return true;
        } 
        else {
            return false;
        }
    }

    public boolean prevPage() {
        if (page > 1) {
            page--;
            return true;
        } 
        else {
            return false;
        }
    }

    public boolean setPage(int page) {
        if (page >= 1 && page <= nbPages) {
            this.page = page;
            return true;
        } 
        else {
            return false;
        }
    }

    public void setTotalElem(int totalElem) {
        this.totalElem = totalElem;
        nbPages = (totalElem + elemByPage -1)/elemByPage;
    }
}
