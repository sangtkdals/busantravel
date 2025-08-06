package busantravel.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceCard extends JPanel {
    public PlaceCard(JFrame frame, String placeName) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 기본 테두리
        setBackground(Color.WHITE); // 배경색

        // 카드 크기 고정 (요청 반영)
        setPreferredSize(new Dimension(180, 180));
        setMinimumSize(new Dimension(180, 180));
        setMaximumSize(new Dimension(180, 180));

        // 이미지 표시 영역 (현재는 단색 배경)
        JLabel imageLabel = new JLabel();
        imageLabel.setBackground(new Color(230, 240, 250)); // 이미지 없을 시 배경색
        imageLabel.setOpaque(true); // 배경색이 보이도록 설정
        imageLabel.setPreferredSize(new Dimension(180, 120)); // 이미지 영역 크기
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // 장소 이름 라벨
        JLabel nameLabel = new JLabel(placeName, JLabel.CENTER);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5)); // 패딩 추가
        nameLabel.setBackground(new Color(245, 245, 245)); // 이름 라벨 배경색
        nameLabel.setOpaque(true); // 배경색이 보이도록 설정

        add(imageLabel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.SOUTH);

        // 클릭 시 상세 정보 팝업 및 호버 효과
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPlaceDetailDialog(frame, placeName);
            }
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스가 카드 위에 올라갔을 때
                setBorder(BorderFactory.createLineBorder(new Color(222, 146, 124), 2)); // 하이라이트 테두리
            }
            @Override
            public void mouseExited(MouseEvent e) { // 마우스가 카드에서 벗어났을 때
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 원래 테두리로 복원
            }
        });
    }

    private void showPlaceDetailDialog(JFrame frame, String placeName) {
        JDialog detailDialog = new JDialog(frame, placeName + " 상세 정보", true);
        detailDialog.setSize(600, 450);
        detailDialog.setLocationRelativeTo(frame);
        detailDialog.setLayout(new BorderLayout());

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailPanel.setBackground(Color.WHITE);

        // PlaceDataLoader에서 장소 상세 정보 불러오기
        PlaceDetailInfo detailInfo = PlaceDataLoader.getPlaceDetail(placeName);
        String descriptionText = "해당 장소의 정보를 찾을 수 없습니다.";
        ImageIcon detailImageIcon = null;

        if (detailInfo != null) {
            descriptionText = detailInfo.getDescription();
            // 이미지 로드 시도
            try {
                // ⭐ 이미지 경로: BusanHow1 패키지 안에 직접 넣어주세요
                detailImageIcon = new ImageIcon(getClass().getResource("/BusanHow1/" + detailInfo.getImagePath()));
            } catch (Exception e) {
                System.err.println("상세 이미지 로드 실패: " + detailInfo.getImagePath() + " - " + e.getMessage());
                // 이미지를 찾을 수 없을 경우 기본 이미지나 경고 메시지 표시 가능
                detailImageIcon = null; // 이미지를 찾지 못했음을 표시
            }
        }

        // 상세 정보 이미지 영역
        JLabel detailImageLabel = new JLabel();
        detailImageLabel.setBackground(new Color(230, 240, 250)); // 기본 배경색
        detailImageLabel.setOpaque(true);
        detailImageLabel.setPreferredSize(new Dimension(500, 400));
        detailImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailImageLabel.setVerticalAlignment(SwingConstants.CENTER);

        if (detailImageIcon != null && detailImageIcon.getImage() != null) {
            // 이미지 크기 조절 (비율 유지)
            Image originalDetailImage = detailImageIcon.getImage();
            int imgWidth = originalDetailImage.getWidth(null);
            int imgHeight = originalDetailImage.getHeight(null);

            int targetWidth = detailImageLabel.getPreferredSize().width;
            int targetHeight = detailImageLabel.getPreferredSize().height;

            if (imgWidth > 0 && imgHeight > 0) { // 유효한 이미지 크기일 경우
                double scaleFactor = Math.min((double) targetWidth / imgWidth, (double) targetHeight / imgHeight);
                Image scaledDetailImage = originalDetailImage.getScaledInstance(
                        (int) (imgWidth * scaleFactor), (int) (imgHeight * scaleFactor), Image.SCALE_SMOOTH);
                detailImageLabel.setIcon(new ImageIcon(scaledDetailImage));
                detailImageLabel.setText(null);
            }
        } else { // ⭐ 이 else 블록을 제대로 닫았습니다.
            detailImageLabel.setText("이미지를 찾을 수 없습니다.");
            // detailImageIcon = null; // 이 줄은 불필요하거나 반복될 수 있습니다. 필요없다면 삭제하세요.
            detailImageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            detailImageLabel.setForeground(Color.RED);
        } // ⭐ 추가된 닫는 중괄호

        JLabel title = new JLabel(placeName);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(50, 50, 50));

        JTextArea description = new JTextArea(descriptionText);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(detailPanel.getBackground());
        description.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        description.setForeground(new Color(80, 80, 80));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setMaximumSize(new Dimension(Integer.MAX_VALUE, description.getPreferredSize().height + 50));

        JButton saveBtn = new JButton("저장하기");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.setBackground(new Color(222, 146, 124));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        saveBtn.setPreferredSize(new Dimension(120, 35));

        saveBtn.addActionListener(e -> JOptionPane.showMessageDialog(detailDialog, placeName + " 저장 완료! 저장된 장소 탭에서 확인하세요."));

        detailPanel.add(detailImageLabel);
        detailPanel.add(Box.createVerticalStrut(15));
        detailPanel.add(title);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(description);
        detailPanel.add(Box.createVerticalStrut(25));
        detailPanel.add(saveBtn);

        detailDialog.add(detailPanel, BorderLayout.CENTER);
        detailDialog.setVisible(true);
    }
}