package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class SkyModel {
    /*
        "sky": {
            "name": "구름많음",
            "code": "SKY_A03"
        }
    */

    // 하늘 상태 코드명
    @SerializedName("name")
    private String name;

    // 하늘 상태 코드
    @SerializedName("code")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
