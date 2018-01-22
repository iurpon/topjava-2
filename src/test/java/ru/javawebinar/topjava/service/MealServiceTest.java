package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.TimeUnit;


import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql" ,config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
     private MealService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    public static StringBuilder results = new StringBuilder();

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }


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
    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }
    @Test
    public void getNotFound() throws Exception{
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID,1);
    }
    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.update(MEAL1, ADMIN_ID);
    }

}