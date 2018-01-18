package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository{
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id",user.getId())
                .addValue("name",user.getName())
                .addValue("email",user.getEmail())
                .addValue("password",user.getPassword())
                .addValue("registered",user.getRegistered())
                .addValue("enabled",user.isEnabled())
                .addValue("caloriesPerDay",user.getCaloriesPerDay());
        if(user.isNew()){
            Number newKey = simpleJdbcInsert.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        }else{
            namedParameterJdbcTemplate.update("UPDATE users set name=:name, email=:email, password=:password" +
                "registered=:registerd, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id",map);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?",id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE id =?",ROW_MAPPER,id);
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public User getByEmail(String email) {
        List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE email =?",ROW_MAPPER,email);

        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name,email",ROW_MAPPER);
    }
}
