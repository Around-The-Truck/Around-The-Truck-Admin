package kr.co.aroundthetruck.admin.dto;

/**
 * Created by sumin on 2015-01-12.
 */
public class CalculatorData {

    int idx;
    int truckIdx;
    String startDate;
    String endDate;

    int todoys_sum;
    int salesPerPerson;

    int[] age;
    int[] gender;

    String[] Menu;

    public CalculatorData(int idx, int truckIdx, String startData, String endDate, int todoys_sum, int salesPerPerson, int[] age, int[]gender, String[]Menu){

        this.idx = idx;
        this.truckIdx = truckIdx;
        this.startDate = startData;
        this.endDate = endDate;
        this.todoys_sum = todoys_sum;
        this.salesPerPerson = salesPerPerson;
        this.age = age;
        this.gender = gender;

    }

    public int getIdx(){
        return idx;
    }

    public int getTruckIdx(){
        return truckIdx;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }






}
