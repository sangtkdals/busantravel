package busantravel.util;

/**
 * Google Places Autocomplete API의 예상 검색어 결과를 저장하는 데이터 모델 클래스입니다.
 * <p>
 * 각 예측은 장소에 대한 설명과 고유한 Place ID를 포함합니다.
 *
 * @see ApiHandler#getAutocompletePredictions(String, String)
 */
public class AutocompletePrediction {

    private final String description;
    private final String placeId;

    public AutocompletePrediction(String description, String placeId) {
        this.description = description;
        this.placeId = placeId;
    }

    /**
     * API가 제공하는 전체 장소 설명(이름, 주소 등)을 반환합니다.
     * 예: "롯데월드타워, 대한민국 서울특별시 송파구 올림픽로"
     * @return 장소 설명 문자열
     */
    public String getDescription() {
        return description;
    }

    /**
     * 장소에 대한 고유 식별자인 Place ID를 반환합니다.
     * 이 ID를 사용하여 Place Details API를 호출할 수 있습니다.
     * @return Place ID 문자열
     */
    public String getPlaceId() {
        return placeId;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "description='" + description + '\'' +
                ", placeId='" + placeId + '\'' +
                '}';
    }
}