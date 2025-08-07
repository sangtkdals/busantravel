package busantravel.util;

import java.util.List;

public class Examples {

    public static void main(String[] args) {
        // ========================================================================
        // Google Maps API 관련 설정
        // ========================================================================
        ApiHandler apiHandler = new ApiHandler();
        String googleApiKey = "AIzaSyCbEjKNhNT9tjRh0KqCil4sUMlF06Dfu5Y";
        
        // --- 1. 장소 자동 완성 ---
        System.out.println("--- 1. 장소 자동 완성 ---");
        String searchInput = "롯데월드";
        List<AutocompletePrediction> predictions = apiHandler.getAutocompletePredictions(searchInput, googleApiKey);
        if (!predictions.isEmpty()) {
            System.out.println("'" + searchInput + "'에 대한 예상 검색어:");
            for (AutocompletePrediction p : predictions) {
                System.out.println(" - " + p.getDescription() + " (ID: " + p.getPlaceId() + ")");
            }
        } else {
            System.out.println("예상 검색어를 찾지 못했습니다.");
        }

        System.out.println("\n============================================\n");

        // --- 2. 장소 상세 정보 및 사진 URL ---
        System.out.println("--- 2. 장소 상세 정보 및 사진 URL ---");
        String placeToSearch = "해운대해수욕장";
        PlaceInfo placeInfo = apiHandler.getPlaceDetailsByName(placeToSearch, googleApiKey);

        if (placeInfo != null) {
            System.out.println("'" + placeToSearch + "'의 정보:");
            System.out.println(" - 이름: " + placeInfo.getName());
            System.out.println(" - 주소: " + placeInfo.getFormattedAddress());
            System.out.println(" - 좌표: " + placeInfo.getLatitude() + ", " + placeInfo.getLongitude());
            
            System.out.println(" - 리뷰: ");
            List<PlaceReview> reviewList = placeInfo.getReviews();
            if (reviewList.isEmpty()) {
                System.out.println("   리뷰가 없습니다.");
            } else {
                for (PlaceReview review : reviewList) {
                    System.out.println("   작성자: " + review.getAuthorName() + " (별점: " + review.getRating() + ")");
                    System.out.println("   내용: " + review.getText());
                }
            }
            
            String photoUrl = apiHandler.getPhotoUrl(placeInfo.getPhotoReference(), 400, googleApiKey);
            if (photoUrl != null) {
                System.out.println(" - 사진 URL: " + photoUrl);
            } else {
                System.out.println(" - 이 장소에는 사진이 없습니다.");
            }
        } else {
            System.out.println("\n'" + placeToSearch + "'에 대한 정보를 가져오는 데 실패했습니다.");
        }

        System.out.println("\n============================================\n");

        // ========================================================================
        // Tmap API 관련 설정
        // ========================================================================
        TmapApiHandler tmapApiHandler = new TmapApiHandler();
        String tmapAppKey = "h5baCQSfp37eFmXohbwx23t7n1KLSIcn3RyFtMcg"; 
        
        TmapRouteResult routeResult = tmapApiHandler.getRouteInfo(
            tmapAppKey,
            129.223088, 35.188301, 
            129.042206, 35.114777,
            "해동용궁사", "부산역"
        );

        // --- 3. 지점 간 소요시간, 비용 안내 (Tmap) ---
        System.out.println("--- 3. Tmap 경로 탐색 결과 ---");
        if (routeResult != null) {
            TmapRouteInfo summary = routeResult.getSummary();
            System.out.printf("총 거리: %.2f km\n", summary.getTotalDistance() / 1000.0);

            int totalSeconds = summary.getTotalTime();
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            System.out.printf("총 소요 시간: %d시간 %d분\n", hours, minutes);

            System.out.printf("총 통행 요금: %,d 원\n", summary.getTotalFare());
            System.out.printf("예상 택시 요금: %,d 원\n", summary.getTaxiFare());
        } else {
            System.out.println("Tmap 경로 정보를 가져오는 데 실패했습니다.");
        }

        System.out.println("\n============================================\n");

        // --- 4. 경로가 표시된 Static Map을 GUI 창에 표시 ---
        System.out.println("--- 4. 경로가 포함된 Static Map 표시 ---");
        if (routeResult != null) {
            StaticMapGenerator mapGenerator = new StaticMapGenerator();
            String mapUrl = mapGenerator.generateMapUrl(routeResult, 540, 480, googleApiKey);
            if (mapUrl != null) {
                // 새로 만든 MapViewer 클래스를 사용하여 GUI 창에 지도 표시
                MapViewer.showMap(mapUrl);

            } else {
                System.out.println("지도 URL 생성에 실패했습니다.");
            }
        } else {
            System.out.println("경로 정보가 없어 지도를 표시할 수 없습니다.");
        }
    }
}