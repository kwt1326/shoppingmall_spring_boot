package com.kwtproject.shoppingmall.repository.user;

import com.kwtproject.shoppingmall.domain.UserEntity;
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
    public UserEntity save(UserEntity user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        return entityManager.createQuery("select m from UserEntity m where m.name = :name", UserEntity.class)
                .setParameter("name", name)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public Optional<UserEntity> findByUserName(String username) {
        return entityManager.createQuery("select m from UserEntity m where m.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultList()
                .stream().findAny();
    }

    @Override
    public List<UserEntity> findAll() {
        return entityManager.createQuery("select m from UserEntity m", UserEntity.class).getResultList();
    }
}
