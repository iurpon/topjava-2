package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.CrudMeal;
import ru.javawebinar.topjava.dao.CrudRealize;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class MealServlet extends HttpServlet {
    private CrudMeal crudMeal;
    DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public MealServlet(){
        crudMeal = new CrudRealize();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if(action != null)
        if(action.equals("delete")){
            int id = Integer.parseInt(req.getParameter("userId"));
            crudMeal.delete(id);
            System.out.println("delete");
        }

        req.setAttribute("list",MealsUtil.getWithExceeded(MealsUtil.mealsC,2000));
        req.getRequestDispatcher("/meals.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));

        try {

            String text = req.getParameter("dateTime");
            text = text.replace("T"," ");

            if(text == null)
                System.out.println("NULL");
            else
            System.out.println(text);
            LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
            meal.setDateTime(parsedDate);


        } catch (Exception e) {
            System.out.println("parse date");
            e.printStackTrace();
        }
        System.out.println("no exeptions");
        crudMeal.add(meal);

        req.setAttribute("list",MealsUtil.getWithExceeded(MealsUtil.mealsC,2000));
        req.getRequestDispatcher("/meals.jsp").forward(req,resp);

    }
}
