package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.util.EnumSet;
import java.util.List;


import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService service;

    @Test
    public void create() throws Exception {
        User newUser = new User(null,"New","new@gmail.com","newPass",
                1500,false, EnumSet.of(Role.ROLE_USER));
        User created = userRepository.save(newUser);
        newUser.setId(created.getId());
        assertMatch(userRepository.getAll(),ADMIN,newUser,USER);
    }

    @Test
    public void delete() throws Exception {
        userRepository.delete(USER_ID);
        assertMatch(userRepository.getAll(),ADMIN);
    }

    @Test
    public void get() throws Exception {
        User user = userRepository.get(100000);
        assertMatch(user,USER);
    }


    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@gmail.com");
        assertMatch(user,ADMIN);
    }

    @Test
    public void update() throws Exception {
        User user = new User(ADMIN);
        user.setName("newAdmin");
        user.setCaloriesPerDay(3000);
        service.update(user);
        assertMatch(service.get(ADMIN_ID),user);

    }

    @Test
    public void getAll() throws Exception {
        List<User>  list = userRepository.getAll();
        assertMatch(list,ADMIN,USER);
    }
    @Test(expected = NotFoundException.class)
    public void notFound(){
        service.delete(1);
    }

}