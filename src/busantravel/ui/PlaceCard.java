package busantravel.ui;

import busantravel.dao.PlaceDAO;
import busantravel.util.ApiHandler;
import busantravel.util.AutocompletePrediction;
import busantravel.util.PlaceInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

public class PlaceCard extends JPanel {
    private PlaceDAO placeDAO;
    private ApiHandler apiHandler;
    private static final String API_KEY = "AIzaSyCbEjKNhNT9tjRh0KqCil4sUMlF06Dfu5Y";

    public PlaceCard(JFrame frame, String placeName, String category, String thumbnail) {
        this.placeDAO = new PlaceDAO();
        this.apiHandler = new ApiHandler();
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

        if (thumbnail != null && !thumbnail.isEmpty()) {
            loadImage(imageLabel, thumbnail);
        } else {
                new SwingWorker<ImageIcon, Void>() {
                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        List<AutocompletePrediction> predictions = apiHandler.getAutocompletePredictions(placeName, API_KEY);
                        if (!predictions.isEmpty()) {
                            String placeId = predictions.get(0).getPlaceId();
                            PlaceInfo placeInfo = apiHandler.getPlaceDetailsById(placeId, API_KEY);
                            if (placeInfo != null && placeInfo.getPhotoReference() != null) {
                                String photoReference = placeInfo.getPhotoReference();
                                String imageUrl = apiHandler.getPhotoUrl(photoReference, 400, API_KEY);
                                Image image = ImageIO.read(new URL(imageUrl));
                                if (image != null) {
                                    return new ImageIcon(image);
                                }
                            }
                        }
                        return null;
                    }

                @Override
                protected void done() {
                    try {
                        ImageIcon imageIcon = get();
                        if (imageIcon != null) {
                            imageLabel.setIcon(imageIcon);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }

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
                showPlaceDetailDialog(frame, placeName, category);
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

    private void showPlaceDetailDialog(JFrame frame, String placeName, String category) {
        JDialog detailDialog = new JDialog(frame, placeName + " 상세 정보", true);
        detailDialog.setSize(600, 450);
        detailDialog.setLocationRelativeTo(frame);
        detailDialog.setLayout(new BorderLayout());

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        detailPanel.setBackground(Color.WHITE);

        // PlaceDAO에서 장소 상세 정보 불러오기
        PlaceDetailInfo detailInfo = placeDAO.getPlaceDetail(placeName, category);
        String descriptionText = "해당 장소의 정보를 찾을 수 없습니다.";

        // 상세 정보 이미지 영역
        JLabel detailImageLabel = new JLabel();
        detailImageLabel.setBackground(new Color(230, 240, 250)); // 기본 배경색
        detailImageLabel.setOpaque(true);
        detailImageLabel.setPreferredSize(new Dimension(500, 400));
        detailImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailImageLabel.setVerticalAlignment(SwingConstants.CENTER);

        if (detailInfo != null) {
            descriptionText = detailInfo.getDescription();
            URL localImageURL = null;
            if (detailInfo.getImagePath() != null && !detailInfo.getImagePath().isEmpty()) {
                localImageURL = getClass().getResource("/BusanHow1/" + detailInfo.getImagePath());
            }

            if (localImageURL != null) {
                ImageIcon detailImageIcon = new ImageIcon(localImageURL);
                Image originalDetailImage = detailImageIcon.getImage();
                int imgWidth = originalDetailImage.getWidth(null);
                int imgHeight = originalDetailImage.getHeight(null);
                int targetWidth = detailImageLabel.getPreferredSize().width;
                int targetHeight = detailImageLabel.getPreferredSize().height;
                if (imgWidth > 0 && imgHeight > 0) {
                    double scaleFactor = Math.min((double) targetWidth / imgWidth, (double) targetHeight / imgHeight);
                    Image scaledDetailImage = originalDetailImage.getScaledInstance((int) (imgWidth * scaleFactor), (int) (imgHeight * scaleFactor), Image.SCALE_SMOOTH);
                    detailImageLabel.setIcon(new ImageIcon(scaledDetailImage));
                    detailImageLabel.setText(null);
                }
            } else {
                detailImageLabel.setText("이미지 로딩 중...");
                new SwingWorker<ImageIcon, Void>() {
                    @Override
                    protected ImageIcon doInBackground() throws Exception {
                        List<AutocompletePrediction> predictions = apiHandler.getAutocompletePredictions(placeName, API_KEY);
                        if (!predictions.isEmpty()) {
                            PlaceInfo placeInfo = apiHandler.getPlaceDetailsById(predictions.get(0).getPlaceId(), API_KEY);
                            if (placeInfo != null && placeInfo.getPhotoReference() != null) {
                                String imageUrl = apiHandler.getPhotoUrl(placeInfo.getPhotoReference(), 400, API_KEY);
                                Image image = ImageIO.read(new URL(imageUrl));
                                if (image != null) {
                                    return new ImageIcon(image);
                                }
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            ImageIcon imageIcon = get();
                            if (imageIcon != null) {
                                Image originalDetailImage = imageIcon.getImage();
                                int imgWidth = originalDetailImage.getWidth(null);
                                int imgHeight = originalDetailImage.getHeight(null);
                                int targetWidth = detailImageLabel.getPreferredSize().width;
                                int targetHeight = detailImageLabel.getPreferredSize().height;
                                if (imgWidth > 0 && imgHeight > 0) {
                                    double scaleFactor = Math.min((double) targetWidth / imgWidth, (double) targetHeight / imgHeight);
                                    Image scaledDetailImage = originalDetailImage.getScaledInstance((int) (imgWidth * scaleFactor), (int) (imgHeight * scaleFactor), Image.SCALE_SMOOTH);
                                    detailImageLabel.setIcon(new ImageIcon(scaledDetailImage));
                                    detailImageLabel.setText(null);
                                }
                            } else {
                                detailImageLabel.setText("이미지를 찾을 수 없습니다.");
                                detailImageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                                detailImageLabel.setForeground(Color.RED);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            detailImageLabel.setText("이미지 로드 중 오류 발생");
                        }
                    }
                }.execute();
            }
        } else {
            detailImageLabel.setText("장소 정보를 찾을 수 없습니다.");
        }

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
        
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setBorder(null);
        descriptionScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        detailPanel.add(descriptionScrollPane);
        detailPanel.add(Box.createVerticalStrut(25));
        detailPanel.add(saveBtn);

        detailDialog.add(detailPanel, BorderLayout.CENTER);
        detailDialog.setVisible(true);
    }

    private void loadImage(JLabel label, String imagePath) {
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                URL url = new URL(imagePath);
                return new ImageIcon(ImageIO.read(url));
            }

            @Override
            protected void done() {
                try {
                    label.setIcon(get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
