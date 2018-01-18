package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/mock.xml"})
@RunWith(SpringRunner.class)
public class InMemorySpringAdminRestControllerTest {

    @Autowired
    private AdminRestController controller;
    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void getAll() throws Exception {
        List<User> list = controller.getAll();
        Assert.assertEquals(list.size(),2);
    }

    @Test
    public void get() throws Exception {
        User user = controller.get(USER_ID);
        Assert.assertEquals(user,USER);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        controller.delete(10);
    }

}