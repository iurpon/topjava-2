package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface MealRepository {
    Meal save(Meal meal,int userId);

    boolean delete(int id,int userId);

    Meal get(int id,int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);


}
