package busantravel.util;

import java.util.List;

/**
 * Google Places API로부터 받은 장소의 상세 정보를 저장하는 데이터 모델 클래스입니다.
 * <p>
 * 이 클래스는 장소의 이름, 포매팅된 주소, 평점, 좌표 및 관련 리뷰 목록을 포함합니다.
 *
 * @see ApiHandler
 * @see PlaceReview
 */
public class PlaceInfo {
    private final String name;
    private final String formattedAddress;
    private final double rating;
    private final List<PlaceReview> reviews;
    private final double latitude;  // 위도
    private final double longitude; // 경도
    private final String photoReference;

    /**
     * 새로운 PlaceInfo 객체를 생성합니다.
     *
     * @param name 장소의 이름
     * @param formattedAddress 전체 주소
     * @param rating 장소에 대한 평균 평점 (1.0 ~ 5.0)
     * @param reviews 장소에 대한 리뷰 목록 (List&lt;PlaceReview&gt;)
     * @param latitude 장소의 위도
     * @param longitude 장소의 경도
     */
    public PlaceInfo(String name, String formattedAddress, double rating, List<PlaceReview> reviews, double latitude, double longitude, String photoReference) {
        this.name = name;
        this.formattedAddress = formattedAddress;
        this.rating = rating;
        this.reviews = reviews;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoReference = photoReference;
    }

    
    /**
     * 장소의 이름을 반환합니다.
     * @return 장소명 (String)
     */
    public String getName() { return name; }
    
    /**
     * 장소의 주소를 반환합니다.
     * @return 주소 (String)
     */
    public String getFormattedAddress() { return formattedAddress; }
    
    /**
     * 장소의 평균 평점을 double 형으로 반환합니다.
     * @return 평균 평점 (double)
     */
    public double getRating() { return rating; }
    
    /**
     * 해당 장소의 리뷰 목록을 List&lt;PlaceReview&gt; 형태로 반환합니다.
     * <p>
     * 리스트의 각 {@link PlaceReview} 객체는 다음과 같은 메소드를 통해 개별 정보에 접근할 수 있습니다.
     * <ul>
     *   <li><b>getAuthorName()</b>: 작성자 닉네임 (String)</li>
     *   <li><b>getRating()</b>: 별점 (long)</li>
     *   <li><b>getText()</b>: 리뷰 본문 (String)</li>
     *   <li><b>getRelativeTimeDescription()</b>: 작성 시기 (String, 예: "2주 전")</li>
     * </ul>
     *
     * <pre>
     * <b>[사용 예제]</b>
     * List&lt;PlaceReview&gt; reviewList = placeInfo.getReviews();
     * for (PlaceReview review : reviewList) {
     *     System.out.println("작성자: " + review.getAuthorName());
     *     System.out.println("별점: " + review.getRating());
     * }
     * </pre>
     *
     * @return 이 장소에 대한 리뷰 목록 ({@code List<PlaceReview>}). 리뷰가 없으면 비어있는 리스트가 반환됩니다.
     */
    public List<PlaceReview> getReviews() { return reviews; }
    
    /**
     * 장소의 위도(Latitude)를 반환합니다.
     * @return 위도 값 (double)
     */
    public double getLatitude() { return latitude; }

    /**
     * 장소의 경도(Longitude)를 반환합니다.
     * @return 경도 값 (double)
     */
    public double getLongitude() { return longitude; }
    
    /**
     * 장소의 대표 사진에 대한 참조 ID를 반환합니다.
     * 이 값은 getPhotoUrl 메소드에 사용될 수 있습니다.
     * @return 사진 참조 ID. 사진이 없는 경우 null.
     */
    public String getPhotoReference() { // <<--- getter 추가
        return photoReference;
    }

    @Override
    public String toString() {
        return "PlaceInfo {" +
                "\n Name: '" + name + '\'' +
                ",\n Address: '" + formattedAddress + '\'' +
                ",\n Rating: " + rating +
                ",\n Latitude: " + latitude +
                ",\n Longitude: " + longitude +
                ",\n Reviews: " + reviews +
                "\n}";
    }
}