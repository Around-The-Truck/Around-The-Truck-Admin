package kr.co.aroundthetruck.admin.dto;

/**
 * Created by 윤석 on 2015-01-17.
 */
public class PayData {// 현금, 카드, 포인트
//    [{"menuIdx":"1", "price":"3000", "type":"0"},{"menuIdx":"2","price":"4000", "type":"1"}]
    public String menuIdx;
    public String price;
    public String type;

    public PayData(String menuIdx, String price, String type){
        this.menuIdx = menuIdx;
        this.price = price;
        this.type = type;
    }
}
