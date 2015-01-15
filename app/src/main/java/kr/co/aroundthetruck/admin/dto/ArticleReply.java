package kr.co.aroundthetruck.admin.dto;

/**
 * Created by 윤석 on 2015-01-11.
 */
public class ArticleReply {

//    {"idx":1,"contents":"와 진짜 맛있어 보이네요 ㅎㅎ",
//            "writer":"01044550423","writer_type":0,"article_idx":1,
//            "reg_date":"2015-01-03 16:52:03"}

    public String idx;
    public String contents;
    public String writer;
    public String writer_type;
    public String article_idx;
    public String reg_date;

    public ArticleReply(String idx, String contents, String writer, String writer_type, String article_idx, String reg_data){
        this.idx = idx;
        this.contents = contents;
        this.writer = writer;
        this.writer_type = writer_type;
        this.article_idx = article_idx;
        this.reg_date = reg_data;
    }
}
