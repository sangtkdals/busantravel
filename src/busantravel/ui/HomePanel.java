package busantravel.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HomePanel extends JPanel {
    private JPanel gridPanel;
    private JFrame frame;
    private JScrollPane scrollPane;
    private Map<String, JButton> categoryButtons = new HashMap<>(); // 카테고리별 버튼 저장
    private String selectedCategory = "관광지"; // 기본 선택 카테고리

    public HomePanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- 상단 카테고리 버튼 바 ---
        JPanel topCategoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topCategoryPanel.setBackground(new Color(250, 250, 250));

        // 순서 변경: 관광지, 맛집, 숙소, 축제
        String[] categories = {"관광지", "맛집", "숙소", "축제"};
        for (String category : categories) {
            JButton btn = new JButton(category);
            btn.setPreferredSize(new Dimension(100, 35));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            btn.addActionListener(e -> {
                selectedCategory = category;
                updateCategoryButtonColors();
                loadPlaces(category);
            });
            categoryButtons.put(category, btn);
            topCategoryPanel.add(btn);
        }
        add(topCategoryPanel, BorderLayout.NORTH);

        // --- 장소 카드 패널 ---
        gridPanel = new JPanel(new GridLayout(0, 4, 30, 30));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        gridPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 초기 버튼 색상 및 장소 로드
        updateCategoryButtonColors();
        loadPlaces(selectedCategory);
    }

    // 선택된 카테고리 버튼 색상 업데이트
    private void updateCategoryButtonColors() {
        for (String category : categoryButtons.keySet()) {
            JButton btn = categoryButtons.get(category);
            if (category.equals(selectedCategory)) {
                btn.setBackground(new Color(180, 90, 70)); // 진한 색 (선택됨)
            } else {
                btn.setBackground(new Color(222, 146, 124)); // 기본 색
            }
        }
    }

    // 장소 데이터 로드 (기존 loadPlaces 메서드 유지)
    private void loadPlaces(String category) {
        gridPanel.removeAll();

        String[] places;

        switch (category) {
            case "맛집":
                places = new String[]{
                        "밀면 맛집", "돼지국밥집", "회센터", "해운대 카페거리",
                        "국제시장 먹자골목", "쌈밥거리", "초량밀면", "동래파전",
                        "BIFF 광장 분식", "해산물 맛집"
                };
                break;
            case "관광지":
                places = new String[]{
                        "해운대 해수욕장", "광안리 해변", "감천문화마을",
                        "자갈치 시장", "송정 해수욕장", "태종대",
                        "부산타워", "오륙도", "동백섬",
                        "용두산 공원", "다대포 해수욕장", "범어사"
                };
                break;
            case "숙소":
                places = new String[]{
                        "해운대 호텔", "광안리 게스트하우스", "남포동 모텔",
                        "송정 펜션", "기장 리조트", "부산역 호텔",
                        "동래 호텔", "해운대 에어비앤비", "럭셔리 풀빌라"
                };
                break;
            case "축제":
                places = new String[]{
                        "부산 불꽃축제", "광안리 갈매기 축제", "부산 바다축제",
                        "부산 국제영화제(BIFF)", "동래읍성 역사축제", "송정 해양축제",
                        "기장 멸치축제", "자갈치 축제", "부산 비엔날레", "부산락페스티벌"
                };
                break;
            default:
                places = new String[]{"데이터 없음"};
        }

        for (int i = 0; i < places.length; i++) {
            gridPanel.add(new PlaceCard(frame, places[i] + " " + (i + 1)));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(0));
    }
}