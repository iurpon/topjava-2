package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

public class CrudRealize implements CrudMeal {
    @Override
    public void add(Meal meal) {
        MealsUtil.mealsC.add(meal);
    }

    @Override
    public void delete(int id) {
        MealsUtil.mealsC.remove(id);
    }

    @Override
    public void update(Meal meal) {
        for(Meal m : MealsUtil.meals){
            if(m.getCount() == meal.getCount()){
                m.setCalories(meal.getCalories());
                m.setDateTime(meal.getDateTime());
                m.setDescription(meal.getDescription());
            }
        }
    }

    @Override
    public void create(Meal meal) {
        add(meal);
    }
}
