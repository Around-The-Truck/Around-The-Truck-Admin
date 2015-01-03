package kr.co.aroundthetruck.admin.dto;

import android.graphics.drawable.Drawable;

/**
 * Created by 윤석 on 2014-12-26.
 */
public class FoodMenuData {
    private Drawable mIcon;
    private String menuName;
    private int menuPrice;

    public FoodMenuData(){

        menuName = "입력되지 않음";
        menuPrice = 100;
    }

    public FoodMenuData(String menuName, int menuPrice){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName(){
        return menuName;
    }

    public int getMenuPrice(){
        return menuPrice;
    }

    public Drawable getIcon(){
        return mIcon;
    }
}
