package com.excilys.formation.service.util;

import com.excilys.formation.pagination.Page;

/**
 * A Util class for services
 * @author kfuster
 *
 */
public class ServiceUtil {
	/**
	 * Copy attributes from to page to another, used when we need the same page but with
	 * a different list of elements (like going from a DTO page to an object page)
	 * @param pPageToCopy the page we want the attributes of
	 * @param pNewPage the page that'll receives the attribute
	 */
	public static <T, K> void copyAttributes(Page<T> pPageToCopy, Page<K> pNewPage) {
		pNewPage.page = pPageToCopy.page;
		pNewPage.elemByPage = pPageToCopy.elemByPage;
		pNewPage.nbPages = pPageToCopy.nbPages;
    	pNewPage.totalElem = pPageToCopy.totalElem;
	}
}
