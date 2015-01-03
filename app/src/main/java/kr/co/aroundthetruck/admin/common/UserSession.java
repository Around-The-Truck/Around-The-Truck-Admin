package kr.co.aroundthetruck.admin.common;

/**
 * Created by sehonoh on 14. 11. 30..
 */
public class UserSession {
    private static UserSession instance;

    private double latitude, longitude;
    private String region;

    public static UserSession getInstance() {
        if (null == instance) {
            synchronized (UserSession.class) {
                instance = new UserSession();
            }
        }

        return instance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
