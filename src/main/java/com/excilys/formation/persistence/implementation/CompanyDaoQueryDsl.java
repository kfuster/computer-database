package com.excilys.formation.persistence.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.QCompany;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * DAO class for companies.
 * @author kfuster
 *
 */

@Transactional
@Repository
public class CompanyDaoQueryDsl implements CompanyDao {
    private static QCompany qCompany = QCompany.company;
    @Autowired
    private SessionFactory sessionFactory;
    
    private Supplier<HibernateQueryFactory> queryFactory =
            () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

    @Override
    public Company getById(long pId) {
        return (Company) queryFactory.get().from(qCompany).where(qCompany.id.eq(pId)).fetchOne();
    }

    @Override
    public void delete(long pId) {
        queryFactory.get().delete(qCompany).where(qCompany.id.eq(pId)).execute();
    }

    @Override
    public Page<Company> getPage(PageFilter pPageFilter) {
        if (pPageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Company> allCompanies = new ArrayList<>();
        Page<Company> pPage = null;
        HibernateQuery<Company> query = queryFactory.get().selectFrom(qCompany);
        int total = (int) query.fetchCount();
        query = query.limit(pPageFilter.getElementsByPage())
                .offset((pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage());
        allCompanies = query.fetch();
        pPage = new Page<>(pPageFilter.getElementsByPage());
        pPage.setPage(pPageFilter.getPageNum());
        pPage.setElements((allCompanies));
        pPage.setTotalElements(total);
        pPageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }

    @Override
    public List<Company> getAll() {
        List<Company> allCompanies = new ArrayList<>();
        allCompanies = queryFactory.get().selectFrom(qCompany).fetch();
        return allCompanies;
    }
}