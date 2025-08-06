package busantravel.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TmapApiHandler {

    private static final String TMAP_URL = "https://apis.openapi.sk.com/tmap/routes?version=1";

    /**
     * 출발지와 도착지의 좌표를 이용하여 Tmap 경로안내 API를 호출하고 결과를 반환합니다.
     *
     * @param appKey     Tmap API 앱 키
     * @param startX     출발지 경도(Longitude)
     * @param startY     출발지 위도(Latitude)
     * @param endX       도착지 경도(Longitude)
     * @param endY       도착지 위도(Latitude)
     * @param startName  출발지 명칭 (URL 인코딩된 상태여야 함)
     * @param endName    도착지 명칭 (URL 인코딩된 상태여야 함)
     * @return TmapRouteInfo 객체, 실패 시 null
     */
    public TmapRouteInfo getRouteInfo(String appKey, double startX, double startY, double endX, double endY, String startName, String endName) {
        HttpURLConnection conn = null;

        try {
            // 요청 본문(JSON) 생성
            String jsonBody = String.format(
                "{\"startX\":%f,\"startY\":%f,\"endX\":%f,\"endY\":%f,\"startName\":\"%s\",\"endName\":\"%s\"," +
                "\"reqCoordType\":\"WGS84GEO\",\"resCoordType\":\"WGS84GEO\",\"tollgateFareOption\":16}",
                startX, startY, endX, endY, startName, endName
            );

            URL url = new URL(TMAP_URL);
            conn = (HttpURLConnection) url.openConnection();

            // POST 방식 설정
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // POST 요청을 위해 반드시 true로 설정
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            // 헤더 설정
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("appKey", appKey);

            // 요청 본문 전송
            try (OutputStream os = conn.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.write(jsonBody);
                writer.flush();
            }

            // 응답 수신
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
                return parseRouteInfo(sb.toString());
            } else {
                System.err.println("Tmap API 호출 실패: " + conn.getResponseCode() + " " + conn.getResponseMessage());
                // 에러 스트림 읽기
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.err.println(line);
                    }
                }
                return null;
            }

        } catch (IOException e) {
            System.err.println("네트워크 오류가 발생했습니다.");
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * Tmap 경로안내 API의 JSON 응답을 파싱하여 TmapRouteInfo 객체로 변환합니다.
     *
     * @param jsonResponse API로부터 받은 JSON 형식의 문자열
     * @return TmapRouteInfo 객체, 파싱 실패 시 null
     */
    private TmapRouteInfo parseRouteInfo(String jsonResponse) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(jsonResponse);
            JSONArray features = (JSONArray) jsonObj.get("features");

            if (features == null || features.isEmpty()) {
                System.err.println("응답에서 'features'를 찾을 수 없거나 비어있습니다.");
                return null;
            }

            JSONObject firstFeature = (JSONObject) features.get(0);
            JSONObject properties = (JSONObject) firstFeature.get("properties");
            
            int totalDistance = ((Number) properties.get("totalDistance")).intValue();
            int totalTime = ((Number) properties.get("totalTime")).intValue();
            int totalFare = ((Number) properties.get("totalFare")).intValue();
            int taxiFare = ((Number) properties.get("taxiFare")).intValue();

            return new TmapRouteInfo(totalDistance, totalTime, totalFare, taxiFare);

        } catch (ParseException e) {
            System.err.println("JSON 파싱에 실패했습니다: " + jsonResponse);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("데이터 처리 중 예외가 발생했습니다.");
            e.printStackTrace();
            return null;
        }
    }
}