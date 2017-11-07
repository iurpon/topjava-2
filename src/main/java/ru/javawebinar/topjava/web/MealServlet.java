package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.CrudMeal;
import ru.javawebinar.topjava.dao.CrudRealize;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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
import java.util.concurrent.CopyOnWriteArrayList;

public class MealServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private CrudRealize crudRealize;
    public MealServlet(){
        crudRealize = new CrudRealize();
    }
    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getServletPath();
        System.out.println(action);
        switch (action){
            case "/new" : showNewForm(req,resp);break;
            case "/delete" : deleteMeal(req,resp);break;
            case "/update" : updateMeal(req,resp);break;
            case "/create" : createMeal(req,resp);break;
            case "/edit" : editMeal(req,resp);break;
            default: showListMeal(req,resp);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("editMeals.jsp");
        dispatcher.forward(request, response);
    }


    private void deleteMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        crudRealize.delete(id);
        resp.sendRedirect("list");


    }
    public void editMeal(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        int id = Integer.parseInt(req.getParameter("id"));
        Meal mealS = crudRealize.getMeal(id);
        System.out.println(mealS);
        req.setAttribute("meal",mealS);
        req.getRequestDispatcher("/editMeals.jsp").forward(req,resp);
    }
    private void updateMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int id = Integer.parseInt(req.getParameter("id"));
        Meal mealS = crudRealize.getMeal(id);
        mealS.setCalories(Integer.parseInt(req.getParameter("calories")));
        mealS.setDescription(req.getParameter("description"));
        crudRealize.update(mealS);
        System.out.println(mealS);
        resp.sendRedirect("list");

    }
    private void showListMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("list",MealsUtil.getWithExceeded(MealsUtil.mealsC,2000));
        req.getRequestDispatcher("/meals.jsp").forward(req,resp);
    }
    private void createMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
    private void addMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}

