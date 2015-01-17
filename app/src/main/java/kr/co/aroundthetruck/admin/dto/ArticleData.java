package kr.co.aroundthetruck.admin.dto;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 윤석 on 2015-01-07.
 */
public class ArticleData {

//    "idx":2,"filename":"default_image.jpg","writer":"1",
//            "writer_type":1,"contents":"맛없다..","like":"like","belong_to":"1",
//            "reg_date":"2014-04-09 16:07:02"

    public String idx;

    public String filename;
    public String truck_filename;
    public String contents;
    public String like;
    public String date;

    public List<ArticleReply> replyList = new ArrayList<>();

    public ArticleData(String idx, String filename, String truck_filename, String contents, String like, String date, List<ArticleReply> replyList){
        this.idx = idx;
        this.filename = filename;
        this.truck_filename = truck_filename;
        this.contents = contents;
        this.like = like;
        this.date = date;

        this.replyList = replyList;
    }

    public void printAll(){
        Log.d("contents : ", contents);
    }


}
