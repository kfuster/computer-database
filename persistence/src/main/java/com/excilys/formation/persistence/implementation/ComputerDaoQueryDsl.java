package com.excilys.formation.persistence.implementation;

import com.excilys.formation.model.Computer;
import com.excilys.formation.model.QCompany;
import com.excilys.formation.model.QComputer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * DAO class for computers.
 *
 * @author kfuster
 */
@Transactional
@Repository
public class ComputerDaoQueryDsl implements ComputerDao {
    private static QComputer qComputer = QComputer.computer;
    private SessionFactory sessionFactory;
    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Computer create(Computer pComputer) {
        if (pComputer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        sessionFactory.getCurrentSession().save(pComputer);
        return pComputer;
    }

    @Override
    public void update(Computer pComputer) {
        if (pComputer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        queryFactory.get().update(qComputer)
                .where(qComputer.id.eq(pComputer.getId()))
                .set(qComputer.name, pComputer.getName())
                .set(qComputer.introduced, pComputer.getIntroduced())
                .set(qComputer.discontinued, pComputer.getDiscontinued())
                .set(qComputer.company, pComputer.getCompany())
                .execute();
    }

    @Override
    public void delete(long pId) {
        queryFactory.get().delete(qComputer).where(qComputer.id.eq(pId)).execute();
    }

    @Override
    public void deleteList(List<Long> idList) {
        queryFactory.get().delete(qComputer).where(qComputer.id.in(idList)).execute();
    }

    @Override
    public void deleteByCompany(long pId) {
        queryFactory.get().delete(qComputer).where(qComputer.company.id.eq(pId)).execute();
    }

    @Override
    public Computer getById(long pId) {
        return (Computer) queryFactory.get().from(qComputer).where(qComputer.id.eq(pId)).fetchOne();
    }

    @Override
    public Computer getByName(String pName) {
        if (pName == null) {
            throw new IllegalArgumentException("A name is needed");
        }
        return (Computer) queryFactory.get().from(qComputer).where(qComputer.name.eq(pName)).fetchOne();
    }

    @Override
    public Page<Computer> getPage(PageFilter pPageFilter) {
        if (pPageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Computer> computers;
        Page<Computer> pPage = new Page<>(pPageFilter.getElementsByPage());
        HibernateQuery<Computer> query = queryFactory.get().selectFrom(qComputer)
                .leftJoin(qComputer.company, QCompany.company);
        Map<String, String> conditions = pPageFilter.getConditions();
        PathBuilder<Computer> computer = new PathBuilder<>(Computer.class, "computer");
        if (conditions != null && !conditions.isEmpty()) {
            if (conditions.containsKey("computerName") && conditions.containsKey("companyName")) {
                query = query.where(qComputer.name.like("%" + conditions.get("computerName") + "%").or(qComputer.company.name.like("%" + conditions.get("companyName") + "%")));
            }
            if (conditions.containsKey("column")) {
                if (conditions.containsKey("order")) {
                    if ("DESC".equals(conditions.get("order"))) {
                        query = query.orderBy(computer.getString(conditions.get("column")).desc());
                    } else {
                        query = query.orderBy(computer.getString(conditions.get("column")).asc());
                    }
                } else {
                    query = query.orderBy(computer.getString(conditions.get("column")).asc());
                }
            }
        }
        int total = (int) query.fetchCount();
        query = query.limit(pPageFilter.getElementsByPage())
                .offset((pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage());
        computers = query.fetch();
        pPage.setPage(pPageFilter.getPageNum());
        pPage.setElements(computers);
        pPage.setTotalElements(total);
        pPageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }
}