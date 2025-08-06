package busantravel.ui;

import java.util.HashMap;
import java.util.Map;

public class PlaceDataLoader {
    private static final Map<String, PlaceDetailInfo> placeData = new HashMap<>();

    static {
        // 더미 데이터 초기화 (향후 DB에서 데이터를 불러오는 방식으로 교체 가능)
        placeData.put("해운대 해수욕장", new PlaceDetailInfo(
                "해운대 해수욕장",
                "부산의 대표적인 해변으로, 넓은 백사장과 아름다운 해안선이 특징입니다. 여름철에는 피서객들로 북적이며 다양한 축제와 행사가 열립니다. 주변에 맛집, 카페, 숙소가 풍부하여 여행하기에 최적의 장소입니다.",
                "haeundae_detail.jpg" // 이미지 파일명
        ));
        placeData.put("광안리 해변", new PlaceDetailInfo(
                "광안리 해변",
                "황령산과 광안대교를 배경으로 멋진 야경을 자랑하는 해변입니다. 특히 밤에는 광안대교의 경관 조명이 아름다움을 더하며, 주기적으로 드론쇼가 펼쳐져 장관을 이룹니다. 젊음과 활기가 넘치는 곳으로 유명합니다.",
                "gwangalli_detail.jpg"
        ));
        placeData.put("감천문화마을", new PlaceDetailInfo(
                "감천문화마을",
                "산비탈에 늘어선 다채로운 색상의 집들이 계단식으로 조화를 이루는 마을입니다. 한국의 마추픽추라 불리며, 골목골목 아기자기한 예술 작품과 벽화가 숨어있어 사진 찍기 좋은 명소로 인기가 많습니다. 독특한 분위기 속에서 추억을 만들어 보세요.",
                "gamcheon_detail.jpg"
        ));
        placeData.put("자갈치 시장", new PlaceDetailInfo(
                "자갈치 시장",
                "부산을 대표하는 최대 수산시장으로, '오이소 보이소 사이소'라는 정감 넘치는 구호로 유명합니다. 싱싱한 해산물을 즉석에서 맛볼 수 있으며, 활기찬 시장 분위기는 그 자체로도 훌륭한 볼거리를 제공합니다. 부산의 삶과 정을 느낄 수 있는 곳입니다.",
                "jagalchi_detail.jpg"
        ));
        // 나머지 장소들도 여기에 추가하시면 됩니다.
        // 예를 들어 HomePanel에 정의된 모든 장소들을 이곳에 추가하여 관리합니다.
        placeData.put("밀면 맛집", new PlaceDetailInfo(
                "밀면 맛집",
                "부산하면 밀면! 시원한 육수와 쫄깃한 면발의 조화가 일품인 밀면은 여름철 무더위를 식히는 데 최고입니다. 각 식당마다 독자적인 맛을 자랑하니, 자신만의 맛집을 찾아보는 재미가 쏠쏠합니다.",
                "milmyeon_detail.jpg"
        ));
        placeData.put("부산 불꽃축제", new PlaceDetailInfo(
                "부산 불꽃축제",
                "매년 가을 광안리 해변에서 열리는 아시아 최대 규모의 불꽃축제입니다. 광안대교를 배경으로 펼쳐지는 화려한 불꽃쇼는 잊을 수 없는 감동을 선사합니다. 수십만 명이 모여 함께 즐기는 장엄한 축제의 현장을 경험해보세요.",
                "fireworks_detail.jpg"
        ));
        // 데이터가 없는 장소를 위한 기본 정보 (HomePanel의 더미 데이터에 추가된 이름 + 번호)
        // 실제 데이터가 없을 경우 'places[i] + " " + (i + 1)' 형태로 이름이 생성되므로, 이를 매칭할 필요가 있습니다.
        // 또는 상세 페이지에서 "데이터 없음" 처리하는 방식으로 유연하게 대응할 수 있습니다.
        // 여기서는 예시로 몇 개만 더 추가해봅니다.
        placeData.put("송정 해수욕장", new PlaceDetailInfo("송정 해수욕장", "서핑으로 유명한 한적한 해변입니다.", "songjeong_detail.jpg"));
        placeData.put("태종대", new PlaceDetailInfo("태종대", "푸른 바다와 기암괴석이 어우러진 해안 절경.", "taejongdae_detail.jpg"));
        placeData.put("부산타워", new PlaceDetailInfo("부산타워", "용두산 공원에 위치한 부산의 랜드마크.", "busantower_detail.jpg"));
        placeData.put("해운대 호텔", new PlaceDetailInfo("해운대 호텔", "해운대 해변 근처의 고급 숙소입니다.", "haeundae_hotel_detail.jpg"));
        placeData.put("광안리 게스트하우스", new PlaceDetailInfo("광안리 게스트하우스", "젊은 여행객을 위한 아늑한 게스트하우스.", "gwangalli_guesthouse_detail.jpg"));
    }

    public static PlaceDetailInfo getPlaceDetail(String placeName) {
        // 더미 데이터에서 정확한 이름이 없을 경우, PlaceCard에서 부여하는 뒤의 ' N'을 제거하고 검색해봅니다.
        // 예를 들어 "해운대 해수욕장 1" 이 들어오면 "해운대 해수욕장"으로 검색
        String baseName = placeName.replaceAll(" \\d+$", ""); // " 숫자" 부분을 제거
        return placeData.getOrDefault(baseName, placeData.get(placeName)); // 먼저 정확한 이름으로 찾고, 없으면 번호 제거하고 다시 찾기
    }
}