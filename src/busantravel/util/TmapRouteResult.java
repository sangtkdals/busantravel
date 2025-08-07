package busantravel.util;

import java.util.List;

/**
 * Tmap 경로안내 API의 전체 응답 결과를 저장하는 데이터 모델 클래스입니다.
 * 경로 요약 정보와 상세 경로 좌표 목록을 모두 포함합니다.
 */
public class TmapRouteResult {

    private final TmapRouteInfo summary; // 요약 정보 (시간, 거리, 비용 등)
    private final List<Coordinate> pathCoordinates; // 경로를 구성하는 상세 좌표 목록

    public TmapRouteResult(TmapRouteInfo summary, List<Coordinate> pathCoordinates) {
        this.summary = summary;
        this.pathCoordinates = pathCoordinates;
    }

    public TmapRouteInfo getSummary() {
        return summary;
    }

    public List<Coordinate> getPathCoordinates() {
        return pathCoordinates;
    }
}