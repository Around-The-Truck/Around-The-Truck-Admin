package kr.co.aroundthetruck.admin.dto;

import android.graphics.drawable.Drawable;

/**
 * Created by 윤석 on 2014-12-26.
 */
public class FoodMenuData {

    //    idx : 메뉴 idx (무쓸모일듯)
//    name : 메뉴 이름
//    price : 가격
//    truck_idx : 트럭 idx
//    photo_filename : 그림파일명
//    description : 메뉴 설명
//    ingredients : 재료..?

    //        [{"photoFieldName":"file0",
//                "name":"맛있는 군인들",
//                "price":"1000",
//                "description":"건강에 좋습니다.",
//                "ingredients":"군인1, 군인2, 군인3"},

    private Drawable mIcon;

    private String menuName;
    private int menuPrice;

    private String photo_filename;
    private String desciption;

    private  String idx;

    public FoodMenuData(){

        menuName = "입력되지 않음";
        menuPrice = 100;
    }

    public FoodMenuData(String menuName, int menuPrice){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public FoodMenuData(String menuName, int menuPrice, String photo_filename, String desciption, String idx){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.photo_filename = photo_filename;
        this.desciption = desciption;
        this.idx = idx;
    }

    public String getMenuName(){
        return menuName;
    }

    public int getMenuPrice(){
        return menuPrice;
    }

    public String getPhoto_filename(){return photo_filename;}

    public String getDesciption(){return desciption;}

    public String getmenuIdx(){return idx;}

    public Drawable getIcon(){
        return mIcon;
    }
}
