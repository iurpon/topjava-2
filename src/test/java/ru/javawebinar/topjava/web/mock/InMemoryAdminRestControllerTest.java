package ru.javawebinar.topjava.web.mock;

import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class InMemoryAdminRestControllerTest {

    private static ConfigurableApplicationContext ctx;
    private static AdminRestController controller;
    private static InMemoryUserRepositoryImpl repository;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/mock.xml");
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        controller = ctx.getBean(AdminRestController.class);
        repository = ctx.getBean(InMemoryUserRepositoryImpl.class);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        ctx.close();
    }

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
        User user = controller.get(1);
        System.out.println("USER__________" + user);
        Assert.assertEquals(user,USER);
    }

    @Test
    public void create() throws Exception {
        User user = new User(null,"new User","user@gmail.ru","noPass",Role.ROLE_USER);
        controller.create(user);
        List<User> list = controller.getAll();
        list.stream().forEach(System.out::println);
        Assert.assertEquals(list.size(),3);
    }

    @Test
    public void delete() throws Exception {
        controller.delete(USER_ID);
        Collection<User> collection = controller.getAll();
        Assert.assertEquals(collection.size(),1);
    }

    @Test
    public void update() throws Exception {
        User newUser = new User(USER_ID,"newUser","user@mail.ru","password", Role.ROLE_USER);
        controller.update(newUser,USER_ID);
        Collection<User> collection = controller.getAll();
        collection.stream().forEach(System.out::println);
        Assert.assertEquals(collection.size(),2);
    }

    @Test
    public void getByMail() throws Exception {
        User user = controller.getByMail("user@yandex.ru");
        Assert.assertEquals(user,USER);
    }

    @Test(expected = NotFoundException.class)
    public void wrongDelete(){
        controller.delete(20);
    }


}