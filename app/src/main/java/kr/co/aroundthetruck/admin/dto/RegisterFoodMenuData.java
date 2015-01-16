package kr.co.aroundthetruck.admin.dto;

/**
 * Created by 윤석 on 2015-01-16.
 */
public class RegisterFoodMenuData {
    //        [{"photoFieldName":"file0",
//                "name":"맛있는 군인들",
//                "price":"1000",
//                "description":"건강에 좋습니다.",
//                "ingredients":"군인1, 군인2, 군인3"},

    public String photoFieldName;
    public String name;
    public String price;
    public String description;
    public String ingredients;

    public RegisterFoodMenuData(String photoFieldName, String name, String price, String description, String ingredients){
        this.photoFieldName = photoFieldName;
        this.name = name;
        this.price = price;
        this.description = description;
        this.ingredients = ingredients;
    }
}
