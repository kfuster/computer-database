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

/**
 * DAO class for Users.
 * @author kfuster
 */

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
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("A user is needed");
        }
        sessionFactory.getCurrentSession().save(user);
        return user;
    }

    @Override
    public Page<User> getPage(PageFilter pageFilter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User getByName(String name) {
        return (User) queryFactory.get().from(qUser).where(qUser.username.eq(name)).fetchOne();
    }

    @Override
    public User getById(long id) {
        return (User) queryFactory.get().from(qUser).where(qUser.id.eq(id)).fetchOne();
    }
}
