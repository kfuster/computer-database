package com.excilys.formation.persistence.implementation;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.QCompany;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

/**
 * DAO class for companies.
 * @author kfuster
 */

@Repository
public class CompanyDaoImpl implements CompanyDao {
    private static QCompany qCompany = QCompany.company;
    private SessionFactory sessionFactory;

    private Supplier<HibernateQueryFactory> queryFactory =
            () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Company getById(long id) {
        return (Company) queryFactory.get().from(qCompany).where(qCompany.id.eq(id)).fetchOne();
    }

    @Override
    public void delete(long id) {
        queryFactory.get().delete(qCompany).where(qCompany.id.eq(id)).execute();
    }

    @Override
    public Page<Company> getPage(PageFilter pageFilter) {
        if (pageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Company> allCompanies;
        Page<Company> pPage;
        HibernateQuery<Company> query = queryFactory.get().selectFrom(qCompany);
        int total = (int) query.fetchCount();
        query = query.limit(pageFilter.getElementsByPage())
                .offset((pageFilter.getPageNum() - 1) * pageFilter.getElementsByPage());
        allCompanies = query.fetch();
        pPage = new Page<>(pageFilter.getElementsByPage());
        pPage.setPage(pageFilter.getPageNum());
        pPage.setElements((allCompanies));
        pPage.setTotalElements(total);
        pageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }

    @Override
    public List<Company> getAll() {
        List<Company> allCompanies;
        allCompanies = queryFactory.get().selectFrom(qCompany).fetch();
        return allCompanies;
    }
}