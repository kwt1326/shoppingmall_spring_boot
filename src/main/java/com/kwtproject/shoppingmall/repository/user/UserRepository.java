package com.kwtproject.shoppingmall.repository.user;

import com.kwtproject.shoppingmall.domain.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements IUserRepository {

    private static Map<Long, UserEntity> store = new ConcurrentHashMap<Long, UserEntity>();
    private static long sequence = 0L;

    public void clearStore() { store.clear(); }

    @Override
    public UserEntity save(UserEntity user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<UserEntity> findByUserName(String name) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(name))
                .findAny();
    }

    @Override
    public List<UserEntity> findAll() {
        return new ArrayList<>(store.values());
    }
}
