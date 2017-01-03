package com.excilys.formation.persistence.implementation;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.QUser;
import com.excilys.formation.model.User;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.UserDao;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class UserDaoImpl implements UserDao {
    private static QUser qUser = QUser.user;
    @Autowired
    private SessionFactory sessionFactory;
    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User create(User pUser) {
        if (pUser == null) {
            throw new IllegalArgumentException("A user is needed");
        }
        sessionFactory.getCurrentSession().save(pUser);
        return pUser;
    }

    @Override
    public Page<User> getPage(PageFilter pPageFilter) throws PersistenceException {
        if (pPageFilter == null) {
            throw new IllegalArgumentException("A PageFilter is needed");
        }
        List<User> allUsers;
        Page<User> pPage;
        HibernateQuery<User> query = queryFactory.get().selectFrom(qUser);
        int total = (int) query.fetchCount();
        query = query.limit(pPageFilter.getElementsByPage())
                .offset((pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage());
        allUsers = query.fetch();
        pPage = new Page<>(pPageFilter.getElementsByPage());
        pPage.setPage(pPageFilter.getPageNum());
        pPage.setElements((allUsers));
        pPage.setTotalElements(total);
        pPageFilter.setNbPage(pPage.getTotalPages());
        return pPage;
    }

    @Override
    public User getByName(String pName) throws PersistenceException {
        return (User) queryFactory.get().from(qUser).where(qUser.username.eq(pName)).fetchOne();
    }

    @Override
    public User getById(long pId) throws PersistenceException {
        return (User) queryFactory.get().from(qUser).where(qUser.id.eq(pId)).fetchOne();
    }
}
