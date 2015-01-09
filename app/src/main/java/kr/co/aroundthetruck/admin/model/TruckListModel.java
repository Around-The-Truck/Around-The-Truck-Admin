package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sehonoh on 2015. 1. 4..
 */
public class TruckListModel {

    @SerializedName("status")
    private int status;

    @SerializedName("result")
    private ArrayList<TruckModel> truckList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<TruckModel> getTruckList() {
        return truckList;
    }

    public void setTruckList(ArrayList<TruckModel> truckList) {
        this.truckList = truckList;
    }
}
