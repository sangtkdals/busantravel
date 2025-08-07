package busantravel.util;

import java.util.List;

/**
 * Google의 Encoded Polyline Algorithm을 사용하여 좌표 목록을 압축된 문자열로 인코딩하는 유틸리티 클래스입니다.
 * <p>
 * URL 길이 제한 문제를 해결하기 위해 Static Map API에 경로를 전달할 때 사용됩니다.
 */
public class PolylineEncoder {

    /**
     * Coordinate 객체 리스트를 인코딩된 폴리라인 문자열로 변환합니다.
     *
     * @param path 인코딩할 좌표의 리스트
     * @return 압축된 폴리라인 문자열
     */
    public static String encode(List<Coordinate> path) {
        long lastLat = 0;
        long lastLng = 0;

        StringBuilder result = new StringBuilder();

        for (final Coordinate point : path) {
            long lat = Math.round(point.getLatitude() * 1e5);
            long lng = Math.round(point.getLongitude() * 1e5);

            long dLat = lat - lastLat;
            long dLng = lng - lastLng;

            encode(dLat, result);
            encode(dLng, result);

            lastLat = lat;
            lastLng = lng;
        }
        return result.toString();
    }

    private static void encode(long v, StringBuilder result) {
        v = v < 0 ? ~(v << 1) : v << 1;
        while (v >= 0x20) {
            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
            v >>= 5;
        }
        result.append(Character.toChars((int) (v + 63)));
    }
}