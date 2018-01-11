package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    Logger log  = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        int userId  = AuthorizedUser.id();
        log.info("MealRestController create meal " + meal ==null? "NULL": meal + " from userId " + userId);
        checkNotFoundWithId(meal,userId);
        checkNew(meal);
        return service.create(meal,userId);
    }

    public void delete(int id){
        int userId  = AuthorizedUser.id();
        log.info("MealRestController delete meal with id " + userId);
        service.delete(id,userId);
    }
    public Meal get(int id){
        int userId  = AuthorizedUser.id();
        log.info("MealRestController get meal with id " + userId);
        return service.get(id,userId);
    }

    public void update(Meal meal){
        int userId  = AuthorizedUser.id();
        log.info("MealRestController update meal " + meal ==null? "NULL": meal + " from userId " + userId);
        checkNotFoundWithId(meal,userId);
        service.update(meal,userId);
    }

    public List<Meal> getAll(){
        log.info("MealRestController getAll");
        return service.getAll();
    }

    @Override
    public String toString() {
        return "MealRestController{}";
    }
}