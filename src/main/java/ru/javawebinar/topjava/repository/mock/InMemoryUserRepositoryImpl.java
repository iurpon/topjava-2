package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer,User> repository  = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        log.info("InMemoryUserRepositoryImpl delete {}", id);
        ValidationUtil.assureIdConsistent(repository.get(id),id);

        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("InMemoryUserRepositoryImpl save {}", user);
        int id = AuthorizedUser.id();
        user.setId(id);
        repository.put(id,user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("InMemoryUserRepositoryImpl get {}", id);
        ValidationUtil.checkNotFoundWithId(repository.get(id),id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("InMemoryUserRepositoryImpl getAll");

        return repository.values().stream()
                .sorted(Comparator.comparing( User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("InMemoryUserRepositoryImpl getByEmail {}", email);
        User user = repository.values().stream()
                .filter( u -> u.getEmail().equals(email))
                .findAny().get();
        ValidationUtil.checkNotFound(user,email);
        return user;
    }
}
