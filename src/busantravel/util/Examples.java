package busantravel.util;

import java.util.List;

public class Examples {

	public static void main(String[] args) {
    	ApiHandler apiHandler = new ApiHandler();
        String apiKey = "AIzaSyAVurerb2PXQ7VU7djEjeK3bgD9kCwwT-I";

        
        // --- 1. 자동 완성 ---
        System.out.println("--- 1. 장소 자동 완성 ---");
        String searchInput = "롯데월드";
        List<AutocompletePrediction> predictions = apiHandler.getAutocompletePredictions(searchInput, apiKey);
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
        
        // 자동 완성 결과의 첫 번째 항목을 사용하거나, 특정 장소를 직접 지정할 수 있음
        String placeToSearch = "해운대해수욕장";
        
        //placeInfo 객체는 장소의 세부 정보와 리뷰, 사진URL 등을 가지고 있음
        PlaceInfo placeInfo = apiHandler.getPlaceDetailsByName(placeToSearch, apiKey);

        if (placeInfo != null) {
        	//장소의 세부 정보 가져오기
            System.out.println("'" + placeToSearch + "'의 정보:");
            System.out.println(" - 이름: " + placeInfo.getName());
            System.out.println(" - 주소: " + placeInfo.getFormattedAddress());
            System.out.println(" - 좌표: " + placeInfo.getLatitude() + ", " + placeInfo.getLongitude());
            System.out.println(" - 리뷰: ");
            List<PlaceReview> reviewList = placeInfo.getReviews();
            
            for (PlaceReview reviews : reviewList) {
                System.out.println("작성자: " + reviews.getAuthorName());
                System.out.println("별점: " + reviews.getRating());
            }
            
            // 사진 URL 가져오기
            String photoUrl = apiHandler.getPhotoUrl(placeInfo.getPhotoReference(), 400, apiKey);
            if (photoUrl != null) {
                System.out.println(" - 사진 URL: " + photoUrl);
            } else {
                System.out.println(" - 이 장소에는 사진이 없습니다.");
            }
        } else {
            System.out.println("\n'" + placeToSearch + "'에 대한 정보를 가져오는 데 실패했습니다.");
        }
        
        // --- 3. 지점 간 소요시간, 비용 안내 ---
        
        TmapApiHandler tmapApiHandler = new TmapApiHandler();
        
        String tmapAppKey = "h5baCQSfp37eFmXohbwx23t7n1KLSIcn3RyFtMcg"; 

        // 예시: 서울 시청 -> 부산 시청
        double startX = 126.9784147;
        double startY = 37.5666805;
        String startName = "%EC%84%9C%EC%9A%B8%EC%8B%9C%EC%B2%AD";

        double endX = 129.0756416;
        double endY = 35.1795543;
        String endName = "%EB%B6%80%EC%82%B0%EC%8B%9C%EC%B2%AD";

        TmapRouteInfo routeInfo = tmapApiHandler.getRouteInfo(tmapAppKey, startX, startY, endX, endY, startName, endName);

        if (routeInfo != null) {
            System.out.println("--- Tmap 경로 탐색 결과 ---");
            System.out.printf("총 거리: %.2f km\n", routeInfo.getTotalDistance() / 1000.0);
            
            int totalSeconds = routeInfo.getTotalTime();
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            System.out.printf("총 소요 시간: %d시간 %d분\n", hours, minutes);
            
            System.out.printf("총 통행 요금: %,d 원\n", routeInfo.getTotalFare());
            System.out.printf("예상 택시 요금: %,d 원\n", routeInfo.getTaxiFare());

            System.out.println("\n원본 데이터:");
            System.out.println(routeInfo);
        } else {
            System.out.println("경로 정보를 가져오는 데 실패했습니다.");
        }
    }
}
