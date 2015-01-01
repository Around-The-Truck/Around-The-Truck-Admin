package kr.co.aroundthetruck.admin.dto;

import java.io.Serializable;

/**
 * Created by 윤석 on 2014-12-26.
 */
public class AdminInformationData implements Serializable {
    private String brandName;
    private String category;
    private String subCategory;
    private String phoneNumber;
    private String openData;

    public AdminInformationData(String brandName, String phoneNumber, String openData, String category, String subCategory){
        this.brandName = brandName;
        this.phoneNumber = phoneNumber;
        this.openData = openData;
        this.category = category;
        this.subCategory = subCategory;

    }

    public String getBrandName(){
        return brandName;
    }

    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }

}
