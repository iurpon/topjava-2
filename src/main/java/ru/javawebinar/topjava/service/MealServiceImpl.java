package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import static ru.javawebinar.topjava.util.ValidationUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;


    @Override
    public Meal create(Meal meal,int userId) {
        return repository.save(meal,userId);
    }

    @Override
    public void delete(int id,int userId) throws NotFoundException {
       repository.delete(id,userId);
    }

    @Override
    public Meal get(int id,int userId) throws NotFoundException {
        return repository.get(id,userId);
    }

    @Override
    public void update(Meal meal,int userId) {
        repository.save(meal,userId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.getAll());
    }
}