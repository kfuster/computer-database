package com.excilys.formation.cd.pages;

import java.util.List;

import com.excilys.formation.cd.dao.IDAO;

public class Page<T> {
	private int elemByPage;
	private int page = 1;
	private int totalElem;
	private int nbPages;
	private List<T> elems;
	private IDAO<T> dao;
	
	public Page(int pElemByPage){
		elemByPage = pElemByPage;
		
	}
	
	public void setDao(IDAO<T> pDAO){
		dao = pDAO;
		calculateNbPages();
		populateList();
	}
	
	public boolean nextPage(){
		if(page < nbPages){
			page++;
			populateList();
			return true;
		}
		else{
			System.out.println("Vous êtes déjà sur la dernière page");
			return false;
		}
	}
	
	public boolean prevPage(){
		if(page > 1){
			page--;
			populateList();
			return true;
		}
		else{
			System.out.println("Vous êtes déjà sur la première page");
			return false;
		}
			
	}
	
	public void populateList(){
		dao.openConnection();
		elems = dao.getAllPaginate(page, elemByPage);
		dao.closeConnection();
	}
	
	public void calculateNbPages(){
		dao.openConnection();
		totalElem = dao.countAll();
		dao.closeConnection();
		nbPages = totalElem / elemByPage;
	}

	public int getElemByPage() {
		return elemByPage;
	}

	public void setElemByPage(int elemByPage) {
		this.elemByPage = elemByPage;
	}

	public int getPage() {
		return page;
	}

	public boolean setPage(int page) {
		if(page >= 1 && page <= nbPages){
			this.page = page;
			populateList();
			return true;
		}
		else{
			System.out.println("Cette page n'est pas valide");
			return false;
		}
	}

	public int getTotalElem() {
		return totalElem;
	}

	public void setTotalElem(int totalElem) {
		this.totalElem = totalElem;
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

	public IDAO<T> getDao() {
		return dao;
	}
	
	
}
