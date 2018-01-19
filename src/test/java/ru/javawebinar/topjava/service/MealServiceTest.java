package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql" ,config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
     MealService service;


    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL1_ID,USER_ID);
        assertMatch(meal,MEAL1);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100005,USER_ID);
        List<Meal> list = service.getAll(USER_ID);
        Assert.assertEquals(list.size(),5);
    }

    @Test
    public void testGetBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> list = service.getAll(USER_ID);
        list.stream().forEach(System.out::println);
        assertMatch(list,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);

        List<Meal> list1 = service.getAll(ADMIN_ID);
        list1.stream().forEach(System.out::println);
        assertMatch(list1,ADMIN_MEAL2,ADMIN_MEAL1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);

        Meal gettedUpdated = service.get(MEAL1_ID, USER_ID);
        assertMatch(gettedUpdated, updated);
    }

    @Test
    public void create() throws Exception {
        Meal meal = getCreated();
        service.create(meal,USER_ID);
        List<Meal> list = service.getAll(USER_ID);
        Assert.assertEquals(list.size(),7);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(MEAL1, ADMIN_ID);
    }

}