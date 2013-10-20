package net.babybaby.agijagi.cooksearch.recommandmeal.mealnutrient;

import android.graphics.drawable.Drawable;

/**
 * Created by FlaShilver on 2013. 10. 20..
 */
public class MealNutrientModel {
    private String name;
    private int cal;
    private Drawable img;
    private String _rc_img_path;

    public MealNutrientModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String get_rc_img_path() {
        return _rc_img_path;
    }

    public void set_rc_img_path(String _rc_img_path) {
        this._rc_img_path = _rc_img_path;
    }
}
