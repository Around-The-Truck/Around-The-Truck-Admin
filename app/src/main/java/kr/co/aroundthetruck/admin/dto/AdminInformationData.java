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
    private String selectPhotoUri;

    public int [] optionClicked;


    public AdminInformationData() {

    }

    public AdminInformationData(String brandName, String phoneNumber, String openData, String category, String subCategory) {
        this.brandName = brandName;
        this.phoneNumber = phoneNumber;
        this.openData = openData;
        this.category = category;
        this.subCategory = subCategory;

    }

    public String getBrandName() {
        return brandName;
    }

    public String getcategoryAll() {
        return category + "-" + subCategory;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOpenData() {

        return openData;
    }

    public void setSelectPhotoUri(String uri) {
        selectPhotoUri = uri;
    }

    public String getSelectPhotoUri() {
        return selectPhotoUri;
    }

    public int getCategory_big_int() {
        return 0;
    }

    public int getCategory_small_int() {
        return 0;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }




    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

}
