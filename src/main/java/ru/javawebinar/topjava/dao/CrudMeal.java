package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface CrudMeal {
    void add(Meal meal);
    void delete(int id);
    void update(Meal meal);
    void create(Meal meal);
    Meal getMeal(int id);
}
