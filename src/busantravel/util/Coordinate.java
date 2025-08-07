package busantravel.util;

/**
 * 위도(latitude)와 경도(longitude) 좌표를 저장하는 데이터 모델 클래스입니다.
 */
public class Coordinate {
    private final double latitude;
    private final double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }
}