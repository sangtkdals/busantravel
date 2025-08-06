package busantravel.util;

public class LocationData {
    private final double latitude;
    private final double longitude;
    private final double accuracy;

    public LocationData(double latitude, double longitude, double accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAccuracy() { return accuracy; }

    @Override
    public String toString() {
        return "LocationData{" + "latitude=" + latitude + ", longitude=" + longitude + ", accuracy=" + accuracy + '}';
    }
}