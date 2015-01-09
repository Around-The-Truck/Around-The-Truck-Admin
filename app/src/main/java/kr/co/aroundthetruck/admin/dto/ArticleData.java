package kr.co.aroundthetruck.admin.dto;

import android.net.Uri;
import android.util.Log;

/**
 * Created by 윤석 on 2015-01-07.
 */
public class ArticleData {

//    "idx":2,"filename":"default_image.jpg","writer":"1",
//            "writer_type":1,"contents":"맛없다..","like":"like","belong_to":"1",
//            "reg_date":"2014-04-09 16:07:02"

    public Uri filename;
    public int writer;
    public int writer_type;
    public String contents;
    public String like;
    public String belong_to;
    public String date;

    public ArticleData(String filename, int writer, int writer_type, String contents, String like, String belong_to, String date){

        this.filename = Uri.parse(filename);
        this.writer = writer;
        this.writer_type = writer_type;
        this.contents = contents;
        this.like = like;
        this.belong_to = belong_to;
        this.like = like;
        this.date = date;
    }

    public void printAll(){
        Log.d("contents : ", contents);
    }


}
