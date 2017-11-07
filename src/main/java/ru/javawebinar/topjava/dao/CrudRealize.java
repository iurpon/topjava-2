package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

public class CrudRealize implements CrudMeal {
    public Meal getMeal(int id){
        for(Meal m : MealsUtil.mealsC){
            if(m.getCount() == id){
                return m;
            }
        }
        return null;
    }
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
        System.out.println("in update");
        for(Meal m : MealsUtil.mealsC){
            if(m.getCount() == meal.getCount()){
                m.setCalories(meal.getCalories());
                m.setDateTime(meal.getDateTime());
                m.setDescription(meal.getDescription());
            }
        }
        System.out.println("after update");
    }

    @Override
    public void create(Meal meal) {
        add(meal);
    }
}
