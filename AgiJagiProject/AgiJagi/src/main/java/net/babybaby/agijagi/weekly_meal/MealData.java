package net.babybaby.agijagi.weekly_meal;

import java.util.ArrayList;

/**
 * @author Namyun
 * @since 13. 10. 19.
 */
public class MealData
{
    private String day;
    private String date;
    int part;
    private ArrayList<String> mealList=new ArrayList<String>();

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day=day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPart()
    {
        return part;
    }

    public void setPart(int part)
    {
        this.part=part;
    }

    public ArrayList<String> getMealList() {
        return mealList;
    }

    public void addMeal(String meal) {
        this.mealList.add(meal);
    }
}
