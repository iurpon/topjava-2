package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m,1));
    }

    @Override
    public Meal save(Meal meal,int userId) {

        log.info(" InMemoryMealRepositoryImpl save " + meal);

        Map<Integer,Meal> current = repository.computeIfAbsent(userId,ConcurrentHashMap::new);
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());

            current.put(meal.getId(),meal);
            return meal;
        }
        repository.get(userId).put(meal.getId(),meal);
        return meal;

    }

    @Override
    public void delete(int id,int userId) {
        log.info(" InMemoryMealRepositoryImpl delete  " + id);
        Map<Integer,Meal> current = repository.get(userId);
        current.remove(id);
    }

    @Override
    public Meal get(int id,int userId) {
        log.info(" InMemoryMealRepositoryImpl get " + id);
        Map<Integer,Meal> current = repository.get(userId);
        return current.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info(" InMemoryMealRepositoryImpl getAll " );
        int userId = AuthorizedUser.id();
        Map<Integer,Meal> current = repository.computeIfAbsent(userId,HashMap::new);

        return current.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

