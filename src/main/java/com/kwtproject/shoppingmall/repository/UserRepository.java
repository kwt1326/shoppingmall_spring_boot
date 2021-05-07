package com.kwtproject.shoppingmall.repository;

import com.kwtproject.shoppingmall.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements IUserRepository {

    private static Map<Long, User> store = new ConcurrentHashMap<Long, User>();
    private static long sequence = 0L;

    public void clearStore() { store.clear(); }

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<User> findByUserName(String name) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
