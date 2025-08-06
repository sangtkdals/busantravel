package busantravel.util;

/**
 * Tmap 경로안내 API의 응답 결과를 저장하는 데이터 모델 클래스입니다.
 * <p>
 * 경로의 총 거리, 총 소요 시간, 통행 요금, 예상 택시 요금 정보를 포함합니다.
 *
 * @see TmapApiHandler
 */
public class TmapRouteInfo {

    private final int totalDistance; // 총 거리 (미터)
    private final int totalTime;     // 총 소요 시간 (초)
    private final int totalFare;     // 총 통행 요금 (톨게이트 비용)
    private final int taxiFare;      // 예상 택시 요금

    /**
     * TmapRouteInfo 객체를 생성합니다.
     *
     * @param totalDistance 총 거리 (단위: 미터)
     * @param totalTime     총 소요 시간 (단위: 초)
     * @param totalFare     총 통행 요금 (단위: 원)
     * @param taxiFare      예상 택시 요금 (단위: 원)
     */
    public TmapRouteInfo(int totalDistance, int totalTime, int totalFare, int taxiFare) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.totalFare = totalFare;
        this.taxiFare = taxiFare;
    }

    /**
     * 경로의 총 거리를 미터(m) 단위로 반환합니다.
     * @return 총 거리 (미터)
     */
    public int getTotalDistance() {
        return totalDistance;
    }

    /**
     * 경로의 총 소요 시간을 초(second) 단위로 반환합니다.
     * @return 총 소요 시간 (초)
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * 경로의 총 통행 요금(톨게이트 비용)을 원(KRW) 단위로 반환합니다.
     * @return 총 통행 요금 (원)
     */
    public int getTotalFare() {
        return totalFare;
    }

    /**
     * 경로의 예상 택시 요금을 원(KRW) 단위로 반환합니다.
     * @return 예상 택시 요금 (원)
     */
    public int getTaxiFare() {
        return taxiFare;
    }

    @Override
    public String toString() {
        return "TmapRouteInfo {" +
                "총 거리=" + totalDistance + "m" +
                ", 총 시간=" + totalTime + "s" +
                ", 통행 요금=" + totalFare + "원" +
                ", 택시 요금=" + taxiFare + "원" +
                '}';
    }
}