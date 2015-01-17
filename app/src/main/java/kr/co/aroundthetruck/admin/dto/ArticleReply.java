package kr.co.aroundthetruck.admin.dto;

/**
 * Created by 윤석 on 2015-01-11.
 */
public class ArticleReply {

//    {"idx":1,"contents":"와 진짜 맛있어 보이네요 ㅎㅎ",
//            "writer":"01044550423","writer_type":0,"article_idx":1,
//            "reg_date":"2015-01-03 16:52:03"}

    public String r_idx;
    public String r_contents;
    public String r_writer;
    public String r_writer_name;
    public String r_writer_filename;
    public String r_article_idx;
    public String r_reg_date;

    public ArticleReply(String idx, String contents, String writer, String writer_name, String writer_filename, String article_idx, String reg_data){
        this.r_idx = idx;
        this.r_contents = contents;
        this.r_writer = writer;
        this.r_writer_name = writer_name;
        this.r_writer_filename = writer_filename;
        this.r_article_idx = article_idx;
        this.r_reg_date = reg_data;
    }
}
