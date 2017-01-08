package com.excilys.formation.persistence.implementation;

import com.excilys.formation.model.QUser;
import com.excilys.formation.model.User;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.UserDao;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

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
    public Page<User> getPage(PageFilter pPageFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getByName(String pName) {
        return (User) queryFactory.get().from(qUser).where(qUser.username.eq(pName)).fetchOne();
    }

    @Override
    public User getById(long pId) {
        return (User) queryFactory.get().from(qUser).where(qUser.id.eq(pId)).fetchOne();
    }
}
