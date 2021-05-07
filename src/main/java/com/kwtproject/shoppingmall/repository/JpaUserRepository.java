package com.kwtproject.shoppingmall.repository;

import com.kwtproject.shoppingmall.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements IUserRepository {

    /* JPA 사용을 위한 객체 */
    private final EntityManager entityManager;

    public JpaUserRepository(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return entityManager.createQuery("select m from User m where m.name = :name", User.class)
                .setParameter("name", name)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return entityManager.createQuery("select m from User m where m.username = :username", User.class)
                .setParameter("username", name)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select m from User m", User.class).getResultList();
    }
}
