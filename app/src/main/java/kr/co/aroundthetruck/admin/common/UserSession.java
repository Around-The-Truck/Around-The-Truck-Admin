package kr.co.aroundthetruck.admin.common;

/**
 * Created by sehonoh on 14. 11. 30..
 */
public class UserSession {
    private static UserSession instance;

    public static UserSession getInstance() {
        if (null == instance) {
            synchronized (UserSession.class) {
                instance = new UserSession();
            }
        }

        return instance;
    }

}
