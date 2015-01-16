package kr.co.aroundthetruck.admin.dto;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sumin on 2015-01-12.
 */
public class CalculatorData {

    String startDate;
    String endDate;

    int todoys_sum;
    int salesPerPerson;

    List<Integer> history_age;
    List<Integer> history_gender; //배열
    List<String> ranking_menu; //배열
    List<Integer> cardcash;

    List<String> timeSperator;
    List<Integer> timeCount;

    public CalculatorData(String startData, String endDate, int todoys_sum, int salesPerPerson, String age, String gender, String menu, String cardcash, String timeSeperator, String timeCount) throws JSONException {

        this.startDate = startData;
        this.endDate = endDate;
        this.todoys_sum = todoys_sum; //그시간대의 합계
        this.salesPerPerson = salesPerPerson; //일인당 평균 지출
        this.history_age = getArrayItem(age);
        this.history_gender = getArrayItem(gender);
        this.ranking_menu = getStringArrayItem(menu);
        this.cardcash = getArrayItem(cardcash);
        this.timeSperator = getStringArrayItem(timeSeperator);
        this.timeCount = getArrayItem(timeCount);

    }

    public int getWomen() {
        return history_gender.get(1);
    }

    public int getMen() {
        return history_gender.get(0);
    }

    public int getHistoryAge(int gen) {

        switch (gen) {
            case 0:
            case 1:
            case 2:
                return history_age.get(gen);
            case 3:
                int a = 0;
                for (int i = 3; i < 10; i++)
                    a += history_age.get(i);
                return a;

        }
        return 0;
    }

    public String getWorkingTime() throws ParseException {

        return transFormatDate(startDate, "a h") + "-" + transFormatDate(endDate, "a h");

    }

    public String[] getLankingMenu() {

        String[] stockArr = new String[ranking_menu.size()];

        stockArr = ranking_menu.toArray(stockArr);
        Log.d("배열 갯수", Integer.toString(stockArr.length));

        return stockArr;
    }


    public int getTodoys_sum() {
        return todoys_sum;
    }

    public int getSalesPerPerson() {
        return salesPerPerson;
    }

    public int getTotalCustomerCount() {

        int totalCount = 0;

        for (int i = 0; i < 10; i++) {
            totalCount += history_age.get(i);
        }

        return totalCount;
    }

    public List<String> getTimeSperator() throws ParseException {

        List<String> seperator = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seperator.add(transFormatDate(timeSperator.get(i), "k"));
        }

        return seperator;
    }

    public List<Integer> getTimeCount() {
        return timeCount;
    }


    public List<Integer> getArrayItem(String bytes) throws JSONException {

        JSONArray arr = new JSONArray(new String(bytes));
        List<Integer> arryItem = new ArrayList<>();

        for (int j = 0; j < arr.length(); j++) {

            arryItem.add(arr.getInt(j));
        }

        return arryItem;
    }

    public List<String> getStringArrayItem(String bytes) throws JSONException {

        JSONArray arr = new JSONArray(new String(bytes));
        List<String> arryItem = new ArrayList<>();

        for (int j = 0; j < arr.length(); j++) {

            arryItem.add(arr.getString(j));
        }

        return arryItem;
    }

    public String transFormatDate(String date, String desiredformat) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date to = transFormat.parse(date);

        transFormat = new SimpleDateFormat(desiredformat);

        return transFormat.format(to);
    }
}