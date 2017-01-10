package com.excilys.formation.persistence.implementation;

import com.excilys.formation.model.Company;
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

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * DAO class for computers.
 * @author kfuster
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {
    private static QComputer qComputer = QComputer.computer;
    private SessionFactory sessionFactory;
    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Computer create(Computer computer) {
        if (computer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        sessionFactory.getCurrentSession().save(computer);
        return computer;
    }

    @Override
    public void update(Computer computer) {
        if (computer == null) {
            throw new IllegalArgumentException("A computer is needed");
        }
        queryFactory.get().update(qComputer)
                .where(qComputer.id.eq(computer.getId()))
                .set(qComputer.name, computer.getName())
                .set(qComputer.introduced, computer.getIntroduced())
                .set(qComputer.discontinued, computer.getDiscontinued())
                .set(qComputer.company, computer.getCompany())
                .execute();
    }

    @Override
    public void delete(long id) {
        queryFactory.get().delete(qComputer).where(qComputer.id.eq(id)).execute();
    }

    @Override
    public void deleteList(List<Long> idList) {
        queryFactory.get().delete(qComputer).where(qComputer.id.in(idList)).execute();
    }

    @Override
    public void deleteByCompany(long id) {
        queryFactory.get().delete(qComputer).where(qComputer.company.id.eq(id)).execute();
    }

    @Override
    public Computer getById(long id) {
        return (Computer) queryFactory.get().from(qComputer).where(qComputer.id.eq(id)).fetchOne();
    }

    @Override
    public Computer getByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("A name is needed");
        }
        return (Computer) queryFactory.get().from(qComputer).where(qComputer.name.eq(name)).fetchOne();
    }

    @Override
    public Page<Computer> getPage(PageFilter pageFilter) {
        if (pageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<Computer> computers;
        Page<Computer> pPage = new Page<>(pageFilter.getElementsByPage());
        HibernateQuery<Computer> query = queryFactory.get().selectFrom(qComputer)
                .leftJoin(qComputer.company, QCompany.company);
        Map<String, String> conditions = pageFilter.getConditions();
        if (conditions != null && !conditions.isEmpty()) {
            query = addConditions(query, conditions);
        }
        int total = getCount(query);
        query = query.limit(pageFilter.getElementsByPage())
                .offset((pageFilter.getPageNum() - 1) * pageFilter.getElementsByPage());
        computers = query.fetch();
        pPage.setPage(pageFilter.getPageNum());
        pPage.setElements(computers);
        pPage.setTotalElements(total);
        pageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }

    /**
     * Method allowing to get the count of a specific query.
     * @param query HibernateQuery<Computer> representing the query where we want to count the result entries.
     * @return int representing the number of result entries of the given query.
     */
    public int getCount(HibernateQuery<Computer> query) {
        return (int) query.fetchCount();
    }

    /**
     * Method adding different conditions to a given query.
     * @param query HibernateQuery<Computer> representing the query where we'll append our conditions.
     * @param conditions Map<String,String> reprensenting the conditions that we'll append to our query.
     * @return HibernateQuery<Computer> a query where the given conditions have been added?
     */
    public HibernateQuery<Computer> addConditions(HibernateQuery<Computer> query, Map<String, String> conditions) {
        if (conditions != null && !conditions.isEmpty()) {
            PathBuilder<Computer> computerPath = new PathBuilder<>(Computer.class, "computer");
            PathBuilder<Company> companyPath = new PathBuilder<>(Company.class, "company");
            if (conditions.containsKey("computerName") && conditions.containsKey("companyName")) {
                query = query.where(qComputer.name.like(conditions.get("computerName") + "%").or(qComputer.company.name.like(conditions.get("companyName") + "%")));
            }
            if (conditions.containsKey("column")) {
                if (conditions.containsKey("order")) {
                    if ("DESC".equals(conditions.get("order"))) {
                        if ("computer".equals(conditions.get("table"))) {
                            query = query.orderBy(computerPath.getString(conditions.get("column")).desc());
                        } else if ("company".equals(conditions.get("table"))) {
                            query = query.orderBy(companyPath.getString(conditions.get("column")).desc());
                        }
                    } else {
                        if ("computer".equals(conditions.get("table"))) {
                            query = query.orderBy(computerPath.getString(conditions.get("column")).asc());
                        } else if ("company".equals(conditions.get("table"))) {
                            query = query.orderBy(companyPath.getString(conditions.get("column")).asc());
                        }
                    }
                } else {
                    if ("computer".equals(conditions.get("table"))) {
                        query = query.orderBy(computerPath.getString(conditions.get("column")).asc());
                    } else if ("company".equals(conditions.get("table"))) {
                        query = query.orderBy(companyPath.getString(conditions.get("column")).asc());
                    }
                }
            }
        }
        return query;
    }
}