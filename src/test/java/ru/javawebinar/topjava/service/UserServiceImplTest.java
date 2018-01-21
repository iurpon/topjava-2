package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)

@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {
    @Autowired
    UserRepository repository;

    @Test
    public void create() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void get() throws Exception {
        User user  = repository.get(USER_ID);
        Assert.assertEquals(user,USER);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = repository.getByEmail("user@yandex.ru");
        assertMatch(user,USER);
    }

    @Test
    public void getAll() throws Exception {
        List<User> list = repository.getAll();
        Assert.assertEquals(list.size(),2);
    }

    @Test
    public void update() throws Exception {
    }

}