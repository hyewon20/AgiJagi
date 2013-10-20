package net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient;

/**
 * Created by FlaShilver on 2013. 10. 20..
 */
public class AllMealNutrientModel {
    private int all_cal=0;
    private int all_tan=0;
    private int all_dan=0;
    private int all_ji=0;

    public int getAll_cal() {
        return all_cal;
    }

    public void setAll_cal(int all_cal) {
        this.all_cal += all_cal;
    }

    public int getAll_tan() {
        return all_tan;
    }

    public void setAll_tan(int all_tan) {
        this.all_tan += all_tan;
    }

    public int getAll_dan() {
        return all_dan;
    }

    public void setAll_dan(int all_dan) {
        this.all_dan += all_dan;
    }

    public int getAll_ji() {
        return all_ji;
    }

    public void setAll_ji(int all_ji) {
        this.all_ji = all_ji;
    }
}
