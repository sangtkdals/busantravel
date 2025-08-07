package busantravel.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Google Static Map API를 사용하여 경로와 핀이 표시된 지도 이미지 URL을 생성하는 클래스입니다.
 * Tmap에서 받은 경로 좌표를 Encoded Polyline 방식으로 압축하여 사용합니다.
 */
public class StaticMapGenerator {

    private static final String STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap";

    /**
     * 경로 정보와 좌표를 바탕으로 Google Static Map 이미지 URL을 생성합니다.
     *
     * @param routeResult  Tmap API로부터 받은 경로 결과 객체
     * @param width        생성할 지도의 너비 (픽셀)
     * @param height       생성할 지도의 높이 (픽셀)
     * @param googleApiKey Google Cloud API 키
     * @return 지도 이미지에 접근할 수 있는 완전한 URL 문자열
     */
    public String generateMapUrl(TmapRouteResult routeResult, int width, int height, String googleApiKey) {
        if (routeResult == null || routeResult.getPathCoordinates().isEmpty()) {
            return null;
        }

        StringBuilder urlBuilder = new StringBuilder(STATIC_MAP_URL);
        urlBuilder.append("?size=").append(width).append("x").append(height);
        urlBuilder.append("&maptype=roadmap");

        List<Coordinate> path = routeResult.getPathCoordinates();
        Coordinate startPoint = path.get(0);
        Coordinate endPoint = path.get(path.size() - 1);

        // 1. 출발지 마커 추가 (파란색 S)
        urlBuilder.append("&markers=color:blue%7Clabel:S%7C").append(startPoint.toString());

        // 2. 도착지 마커 추가 (빨간색 E)
        urlBuilder.append("&markers=color:red%7Clabel:E%7C").append(endPoint.toString());

        // 3. 경로를 Encoded Polyline으로 압축하여 추가
        String encodedPolyline = PolylineEncoder.encode(path);
        
        String urlSafePolyline;
		try {
			urlSafePolyline = URLEncoder.encode(encodedPolyline, "UTF-8");
			urlBuilder.append("&path=color:0x0000ff%7Cweight:4%7Cenc:").append(urlSafePolyline);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        // --- 여기까지 수정 ---

        urlBuilder.append("&key=").append(googleApiKey);
        
        return urlBuilder.toString();
    }
    
    // (main 메소드는 Examples.java로 이전되었으므로 여기서는 생략)
}